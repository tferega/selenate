package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqBrowserCapture
import net.selenate.common.comms.res.SeResBrowserCapture
import net.selenate.common.comms.{ SeCapturedFrame, SeCapturedWindow, SeCookie }
import org.apache.commons.io.IOUtils
import org.openqa.selenium.{ Cookie, OutputType}
import org.openqa.selenium.WebElement
import scala.collection.JavaConversions._

object BrowserCaptureAction {
  val EmptyPicture = getResource("emptypic.png")
  val ErrorPicture = getResource("errorpic.png")
  val ErrorSource  = new String(getResource("errorsource.html"), "UTF-8")

  private lazy val clazz = this.getClass()
  private def getResource(name: String): Array[Byte] =
    IOUtils.toByteArray(clazz.getResourceAsStream(name))
}

class BrowserCaptureAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqBrowserCapture, SeResBrowserCapture]
    with ActionCommons {
  import BrowserCaptureAction._

  private case class FrameInfo(path: FramePath, index: FrameNum, name: String, src: String, html: String)

  def act = { arg =>
    val captureTime = System.currentTimeMillis

    new SeResBrowserCapture(
        arg.getName,
        captureTime,
        seqToRealJava(getWindowList(arg.isTakeScreenshot)))
  }

  private def getWindowList(isTakeScreenshot: Boolean): List[SeCapturedWindow] = {
    if(context.useFrames) {
      val windowHandleList = tryWithAlternative("getting a list of window handles", List.empty[WindowHandle]) {
        d.getWindowHandles.toList
      }

      windowHandleList flatMap { windowHandle =>
        tryWithAlternative("capturing a window", Option.empty[SeCapturedWindow]) {
          val frameList = getChildFrameList(windowHandle, Vector.empty[Int])
          windowSwitch(windowHandle)
          captureWindow(isTakeScreenshot, frameList)
        }
      }
    } else {
      captureWindow(isTakeScreenshot, List.empty).toList
    }
  }

  private def getChildFrameList(windowHandle: WindowHandle, framePath: FramePath): List[SeCapturedFrame] = {
    val rawFrameList = tryWithAlternative("finding all frames", List.empty[(WebElement, Int)]) {
      findAllFrames().toList.zipWithIndex
    }

    val frameInfoList = rawFrameList flatMap { case (elem, index) =>
      tryWithAlternative("parsing frame info", Option.empty[FrameInfo]) {
        Some(FrameInfo(
          framePath :+ index,
          index,
          elem.getAttribute("name"),
          elem.getAttribute("src"),
          captureHtml))
      }
    }

    frameInfoList flatMap { frameInfo =>
      tryWithAlternative("constructing child frame list", List.empty[SeCapturedFrame]) {
        fullSwitch(windowHandle, frameInfo.path)
        val childFrameList = getChildFrameList(windowHandle, frameInfo.path)
        childFrameList :+ new SeCapturedFrame(windowHandle, frameInfo.path map toInteger, frameInfo.index, frameInfo.name, frameInfo.src, frameInfo.html)
      }
    }
  }

  private def captureWindow(isTakeScreenshot: Boolean, frameList: List[SeCapturedFrame]): Option[SeCapturedWindow] =
    tryWithAlternative("a window", Option.empty[SeCapturedWindow]) {
      Some(new SeCapturedWindow(
          d.getTitle(),
          d.getCurrentUrl(),
          d.getWindowHandle(),
          d.manage.window.getPosition.getX(),
          d.manage.window.getPosition.getY(),
          d.manage.window.getSize.width,
          d.manage.window.getSize.height,
          setToRealJava(captureCookieSet()),
          captureHtml(),
          captureScreenshot(isTakeScreenshot),
          seqToRealJava(frameList)))
    }

  private def captureCookieSet() = {
    val cookieSet = tryWithAlternative("capturing a set of cookies", Set.empty[Cookie]) {
      d.manage.getCookies.toSet
    }

    cookieSet flatMap { cookie =>
      tryWithAlternative("parsing a cookie", Option.empty[SeCookie]) {
        Some(parseCookie(cookie))
      }
    }
  }

  private def captureHtml(): String = {
    tryWithAlternative("the source", ErrorSource) {
      d.getPageSource()
    }
  }

  private def captureScreenshot(isTakeScreenshot: Boolean): Array[Byte] =
    isTakeScreenshot match {
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
        logWarn(s"An error occured while $desc!", e)
        alternative
    }
}
