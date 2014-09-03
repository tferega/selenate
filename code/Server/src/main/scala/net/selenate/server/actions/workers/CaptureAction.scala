package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms._
import net.selenate.common.comms.req.SeReqCapture
import net.selenate.common.comms.res.SeResCapture
import net.selenate.common.user.{ Cookie => SelenateCookie }
import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.IOUtils
import org.openqa.selenium.{ Cookie, OutputType}
import scala.collection.JavaConversions._

object CaptureAction {
  val EmptyPicture = getResource("emptypic.png")
  val ErrorPicture = getResource("errorpic.png")
  val ErrorSource  = new String(getResource("errorsource.html"), "UTF-8")
  val ErrorCookies = Set.empty[SelenateCookie]
  val ErrorWindows = List.empty[SeWindow]

  private lazy val clazz = this.getClass()
  private def getResource(name: String): Array[Byte] =
    IOUtils.toByteArray(clazz.getResourceAsStream(name))

  object XPath {
    val AllChildren = "*"
  }

  object JS {
    val getAttributes =
      """|var report = [];
         |var attrList = arguments[0].attributes;
         |for (var n = 0; n < attrList.length; n++) {
         |  var entry = attrList[n];
         |  report.push(entry.name + ' -> ' + entry.value);
         |};
         |return report;""".stripMargin
  }
}

class CaptureAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqCapture, SeResCapture]
    with ActionCommons {
  import CaptureAction._

  private case class FrameInfo(index: Int, name: String, src: String)

  def act = { arg =>
    val captureTime = System.currentTimeMillis
    val cookieList = tryWithAlternative("a list of cookies", ErrorCookies)(getCookieList)
    val windowList = tryWithAlternative("a list of windows", ErrorWindows)(getWindowList(arg.takeScreenshot))

    new SeResCapture(
        arg.name,
        captureTime,
        setToRealJava(cookieList),
        seqToRealJava(windowList))
  }

  private def getCookieList =
    d.manage.getCookies.toSet map toSelenate

  private def getWindowList(takeScreenshot: Boolean) = {
    if(context.useFrames) {
      d.getWindowHandles.toList.map(wh => getWindow(wh, takeScreenshot))
    } else {
      List(getWindow(d.getWindowHandle(), takeScreenshot))
    }
  }

  private def getWindow(windowHandle: String, takeScreenshot: Boolean) = {
    if(context.useFrames) {
      switchToWindow(windowHandle)
    }

    val html = getHtml
    val screenshot = getScreenshot(takeScreenshot)

    new SeWindow(
        d.getTitle,
        d.getCurrentUrl,
        windowHandle,
        d.manage.window.getPosition.getX,
        d.manage.window.getPosition.getY,
        d.manage.window.getSize.width,
        d.manage.window.getSize.height,
        getHtml,
        getScreenshot(takeScreenshot),
        seqToRealJava(getRootFrames(windowHandle)))
  }

  private def getRootFrames(windowHandle: String): List[SeFrame] =
    findAllFrames map { f =>
      getFrame(windowHandle, Vector.empty, f)
    }

  private def getFrame(windowHandle: String, framePath: Vector[Int], frame: FrameInfo): SeFrame = {
    val fullPath = framePath :+ frame.index
    switchToFrame(windowHandle, fullPath)

    val name       = frame.name
    val src        = frame.src
    val html       = getHtml

    val frameList = findAllFrames map { f =>
      getFrame(windowHandle, fullPath, f)
    }

    new SeFrame(frame.index, name, src, html, windowHandle, seqToRealJava(frameList))
  }

  private implicit def toSelenate(cookie: Cookie): SelenateCookie = new SelenateCookie(
    cookie.getName,
    cookie.getValue,
    cookie.getDomain,
    cookie.getPath,
    cookie.getExpiry,
    cookie.isSecure)

  private def findAllFrames: List[FrameInfo] = {
    if(context.useFrames) {
      val raw = d.findElementsByXPath("//*[local-name()='frame' or local-name()='iframe']").toList.zipWithIndex
      raw map { case (elem, index) =>
        FrameInfo(index, elem.getAttribute("name"), elem.getAttribute("name"))
      }
    } else List.empty
  }


  private def getHtml: String = {
    tryWithAlternative("the source", ErrorSource) {
      d.getPageSource
    }
  }

  private def getScreenshot(takeScreenshot: Boolean): Array[Byte] = takeScreenshot match {
    case true  =>
      tryWithAlternative("a screenshot", ErrorPicture) {
        d.getScreenshotAs(OutputType.BYTES)
      }
    case false =>
      EmptyPicture
  }

  private def tryWithAlternative[T](desc: String, alternative: T)(f: => T): T =
    try {
      f
    } catch {
      case e: Exception =>
        logWarn(s"An error occured while capturing $desc", e)
        alternative
    }
}
