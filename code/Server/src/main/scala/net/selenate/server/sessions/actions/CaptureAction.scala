package net.selenate
package server
package sessions
package actions

import common.comms._
import res._
import req._
import org.openqa.selenium.{ Cookie, OutputType, WebElement }
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.firefox.FirefoxDriver
import scala.collection.JavaConversions._
import org.apache.commons.codec.binary.Base64

object CaptureAction {
  private val EmptyPicture = Base64.decodeBase64("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".getBytes)

  object XPath {
    val AllChildren = "*"
  }

  object JS {
    val getAttributes = """
var report = [];
var attrList = arguments[0].attributes;
for (var n = 0; n < attrList.length; n++) {
  var entry = attrList[n];
  report.push(entry.name + ' -> ' + entry.value);
};
return report;
"""
  }
}

class CaptureAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends IAction[SeReqCapture, SeResCapture]
    with ActionCommons {
  import CaptureAction._

  protected val log = Log(classOf[CaptureAction])

  private case class FrameInfo(index: Int, name: String, src: String)

  def act = { arg =>
    new SeResCapture(
        arg.name,
        System.currentTimeMillis,
        setToRealJava(getCookieList),
        seqToRealJava(getWindowList(arg.takeScreenshot)))
  }

  private def getCookieList =
    d.manage.getCookies.toSet map toSelenate

  private def getWindowList(takeScreenshot: Boolean) = {
    if(context.useFrames) d.getWindowHandles.toList map { wh => getWindow(wh, takeScreenshot)}
    else List(getWindow(d.getWindowHandle(), takeScreenshot))
  }

  private def getWindow(windowHandle: String, takeScreenshot: Boolean) = {
    if(context.useFrames) switchToWindow(windowHandle)
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

  private implicit def toSelenate(cookie: Cookie): SeCookie = new SeCookie(
    cookie.getDomain,
    cookie.getExpiry,
    cookie.getName,
    cookie.getPath,
    cookie.getValue,
    cookie.isSecure)

  private def findAllFrames: List[FrameInfo] = {
    if(context.useFrames) {
      val raw = d.findElementsByXPath("//*[local-name()='frame' or local-name()='iframe']").toList.zipWithIndex
      raw map { case (elem, index) =>
        FrameInfo(index, elem.getAttribute("name"), elem.getAttribute("name"))
      }
    } else List.empty
  }


  private def getHtml       = d.getPageSource
  private def getScreenshot(takeScreenshot: Boolean) = takeScreenshot match {
    case true  => d.getScreenshotAs(OutputType.BYTES)
    case false => EmptyPicture
  }
}
