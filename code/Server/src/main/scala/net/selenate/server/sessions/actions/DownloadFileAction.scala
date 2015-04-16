package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import java.util.ArrayList
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.RemoteWebElement
import scala.collection.JavaConverters._
import java.nio.file.FileSystems
import java.nio.file.StandardWatchEventKinds._
import java.nio.file.Path
import java.io.File
import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.WatchService
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import java.nio.file.WatchEvent
//import scala.concurrent.ExecutionContext.Implicits.global

class DownloadFileAction(val d: FirefoxDriver, sessionID: String)(implicit context: ActionContext)
    extends IAction[SeReqDownloadFile, SeResDownloadFile]
    with ActionCommons {

  protected val log = Log(classOf[DownloadFileAction])

  def act = { arg =>
    log.info("Invoked DownloadFileAction for %s".format(arg.toString()))

    val watchService = FileSystems.getDefault().newWatchService()
    val dir     = new File("/tmp/ff-downloads/%s".format(sessionID))
    if (!dir.isDirectory()) dir.mkdir()
    val path     = dir.toPath()

    path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY)
    log.info("Registered watch service on %s".format(dir.toString()))

//    val findelementreq = new SeReqFindElement(arg.method, arg.query)
//    val metadata = new FindElementAction(d).act(findelementreq).element

    log.info("Before click...")
    new ClickAction(d).act(new SeReqClick(arg.windowHandle, arg.framePath, arg.method, arg.query))
    log.info("After click...")

    try{
      val (ba, ext) = Await.result(Future{tryPollResult(path, watchService)}, 30 seconds)
      new SeResDownloadFile(ba, ext)
    } catch {
      case e: Throwable =>
        log.error("No file was created under firefox downloads", e)
        throw new IllegalArgumentException(e)
    } finally {
      watchService.close()
    }

  }

  private def listenerOverFile(parentDir: Path, filePath: Path): (Array[Byte], String) = {
    val f = dumpFile(parentDir.resolve(filePath).toFile())
    /*
     * Firefox creates partial files while downloading. On download complete, partials are deleted and real file is filled
     */
    if (!(f._1.length == 0 && parentDir.toFile.list().exists(_.endsWith("part")))) {
      f
    }
    else {
      Thread.sleep(500)
      listenerOverFile(parentDir, filePath)
    }
  }

  private def tryPollResult(parentPath: Path, watchService: WatchService): (Array[Byte], String) = {
    try{
      watchService.take().pollEvents().asScala.find{ e=>
//        case e if e.kind == ENTRY_MODIFY && counter == 1 => tryPollResult(parentPath, watchService, counter + 1)
        val cond = e.context().isInstanceOf[Path] && e.kind == ENTRY_CREATE && !e.context.asInstanceOf[Path].toString().endsWith("part")
        log.info("Event %s fired for %s".format(e.kind, e.context.asInstanceOf[Path].toString()))
        cond
//          val path = e.context.asInstanceOf[Path]
//          (e.kind, path)
      } match {
        case Some(event) =>
          log.info("Fired blocking await for resolve file %s".format(event.context().asInstanceOf[Path].toString))
          val res = Await.result(Future{listenerOverFile(parentPath, event.context().asInstanceOf[Path])}, 30 seconds)
          log.info("Resolved download for file %s".format(event.context().asInstanceOf[Path].toString))
          res
        case None => tryPollResult(parentPath, watchService)
      }

//      val resultEvent: Option[(WatchEvent.Kind[_], Path)] =
//        events.filter{
//          case (kind, path) =>
//            log.info(path.toString)
//            log.info("+++++++" + !path.toString.endsWith("part"))
//            kind == ENTRY_CREATE &&
//            !path.toString.endsWith("part") &&
//            {
//             val (binary, ext) = dumpFile(parentPath.resolve(path).toFile)
//             binary.length
//            }> 0
//        }.headOption
//
//      resultEvent match {
//        case Some((kind, path)) =>
//
//          log.info("-- File was created: %s".format(path.getFileName()))
//
//          val file = parentPath.resolve(path).toFile
//          dumpFile(file) match {
//            case (ba, ext) if ba.length == 0 =>
//              log.info("-- File was created but contains empty data: %s".format(path.getFileName()))
//              tryPollResult(parentPath, watchService)
//            case rest =>
//              file.delete()
//              rest
//          }
//        case None       =>
//          Thread.sleep(200)
//          tryPollResult(parentPath, watchService)
//      }
    } catch {
      case ie: InterruptedException =>
        log.error("tryPollResult was interrupted", ie)
        throw ie
      case other: Throwable         =>
        log.error("Error on tryPoll", other)
        throw other
    }

  }

//  private def tryPollResult(parentPath: Path, watchService: WatchService): (Array[Byte], String) = {
//    try{
//      val events = watchService.take().pollEvents().asScala.collect{
////        case e if e.kind == ENTRY_MODIFY && counter == 1 => tryPollResult(parentPath, watchService, counter + 1)
//        case e if e.context().isInstanceOf[Path] =>
//          val path = e.context.asInstanceOf[Path]
//          log.info("Event %s fired for %s".format(e.kind, path))
//          (e.kind, path)
//      }
//
//      val resultEvent: Option[(WatchEvent.Kind[_], Path)] =
//        events.filter{
//          case (kind, path) =>
//            log.info(path.toString)
//            log.info("+++++++" + !path.toString.endsWith("part"))
//            kind == ENTRY_CREATE &&
//            !path.toString.endsWith("part") &&
//            {
//             val (binary, ext) = dumpFile(parentPath.resolve(path).toFile)
//             binary.length
//            }> 0
//        }.headOption
//
//      resultEvent match {
//        case Some((kind, path)) =>
//
//          log.info("-- File was created: %s".format(path.getFileName()))
//
//          val file = parentPath.resolve(path).toFile
//          dumpFile(file) match {
//            case (ba, ext) if ba.length == 0 =>
//              log.info("-- File was created but contains empty data: %s".format(path.getFileName()))
//              tryPollResult(parentPath, watchService)
//            case rest =>
//              file.delete()
//              rest
//          }
//        case None       =>
//          Thread.sleep(200)
//          tryPollResult(parentPath, watchService)
//      }
//    } catch {
//      case ie: InterruptedException =>
//        log.error("tryPollResult was interrupted", ie)
//        throw ie
//      case other: Throwable         =>
//        log.error("Error on tryPoll", other)
//        throw other
//    }
//
//  }

  private def dumpFile(file: File): (Array[Byte], String) = {
    val ext  = FilenameUtils.getExtension(file.toString)
    val ba = FileUtils.readFileToByteArray(file)
    (ba, ext)
  }
}
