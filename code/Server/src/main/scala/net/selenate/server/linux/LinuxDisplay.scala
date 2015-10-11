package net.selenate.server
package linux

import com.ferega.procrun._
import net.selenate.common.exceptions.SeException
import org.joda.time.DateTime
import scala.annotation.tailrec

object LinuxDisplay extends Loggable {
  private val screenCache = new SetCache[Int]()
  private val BaseNum = 200

  private val width  = C.Server.Pool.DISPLAY_WIDTH
  private val height = C.Server.Pool.DISPLAY_HEIGHT

  def create(): DisplayInfo = {
    try {
      val num = getFirstFree()
      create(num)
    } catch {
      case e: Exception =>
        e.printStackTrace();
        throw e;
    }
  }

  def record(name: String, num: Int): String = {
    val dt = formattedCurrentDateTime()
    val filename = C.Server.Locations.RECORDINGS
        .replace("$DT", dt)
        .replace("$NAME", name)
    runFFmpeg(filename, num)
    filename
  }

  def destroy(displayInfo: DisplayInfo) {
    val num = displayInfo.num
    logDebug(s"""Destroying screen $num""")
    runPkill(s"ffmpeg.*:$num")
    runPkill(s"x11vnc.*:$num")
    runPkill(s"icewm.*:$num")
    runPkill(s"Xvfb.*:$num")
  }

  def destroyAll() {
    logDebug(s"""Destroying all screens""")
    runPkill(s"ffmpeg.*")
    runPkill(s"x11vnc.*")
    runPkill(s"icewm.*")
    runPkill(s"Xvfb.*")
  }

  private def create(num: Int): DisplayInfo = {
    logDebug(s"""Creating screen number $num""")
    screenCache.add(num)

    val result = for {
      xvfb   <- runXvfb(num).right
      iceWM  <- runIceWM(num).right
      x11vnc <- runX11vnc(num).right
    } yield {
      waitFor(extractX11vncPort(x11vnc), 2000, 150).getOrElse(throw new SeException("x11vnc failed to start properly (could not parse output)"))
    }

    result match {
      case Left(message)   =>
        val msg = s"""An error occured while creating screen $num:\n$message"""
        logError(msg)
        throw new SeException(msg)
      case Right(port) =>
        logDebug(s"""Screen $num successfully created on port $port""")
        DisplayInfo(num, port)
    }
  }

  private def runXvfb(num: Int)         = LinuxProc.runAndVerify("Xvfb", s":$num" | "-screen" | "0" | s"${width}x${height}x16" | "-ac")
  private def runIceWM(num: Int)        = LinuxProc.runAndVerify("icewm", Seq(s"--display=:$num"))
  private def runX11vnc(num: Int)       = LinuxProc.runAndVerify("x11vnc", "-display" | s":$num" | "-listen" | "localhost" | "-nopw" | "-xkb" | "-shared" | "-forever")
  private def runXdpyInfo(num: Int)     = LinuxProc.runAndEnd("xdpyinfo", "-display" | s":$num")
  private def runPkill(pattern: String) = LinuxProc.runAndEnd("pkill", "-f" | pattern)
  private def runFFmpeg(filename: String, num: Int) = LinuxProc.runAndVerify("ffmpeg",
        "-y"          |
        "-video_size" | s"${width}x${height}" |
        "-framerate"  | "25"                  |
        "-f"          | "x11grab"             |
        "-i"          | s":$num"              |
        filename)

  @tailrec
  private def getFirstFree(num: Int = BaseNum): Int =
    if (isFree(num)) num else getFirstFree(num+1)

  private def isFree(num: Int) = {
    (num > BaseNum) &&                                      // Sanity check
    runXdpyInfo(num).contains("unable to open display") &&  // System
    !screenCache.checkAndReserve(num)                       // Thread-safe cache
  }

  private def formattedCurrentDateTime(): String =
    DateTime.now().toString("YYYY-MM-dd'T'HHmmss")

  val PortR = """PORT=(\d+)"""r
  private def extractX11vncPort(x11vncProc: RunningProcess): Option[Int] =
    tryo {
      val out = x11vncProc.stdOutSoFar.trim
      val PortR(port) = out
      port.toInt
    }
}
