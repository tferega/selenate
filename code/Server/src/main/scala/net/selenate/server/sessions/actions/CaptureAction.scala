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

object CaptureAction {
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

class CaptureAction(val d: FirefoxDriver)
    extends IAction[SeReqCapture, SeResCapture]
    with ActionCommons {
  import CaptureAction._

  private case class FrameInfo(index: Int, name: String, src: String)

  def act = { arg =>
    new SeResCapture(
        arg.name,
        System.currentTimeMillis,
        setToRealJava(getCookieList),
        seqToRealJava(getWindowList))
  }

  private def getCookieList =
    d.manage.getCookies.toSet map toSelenate

  private def getWindowList =
    d.getWindowHandles.toList map getWindow

  private def getWindow(windowHandle: String) = {
    switchToWindow(windowHandle)
    new SeWindow(
        d.getTitle,
        d.getCurrentUrl,
        windowHandle,
        d.manage.window.getPosition.getX,
        d.manage.window.getPosition.getY,
        d.manage.window.getSize.width,
        d.manage.window.getSize.height,
        getHtml,
        getScreenshot,
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
    val raw = d.findElementsByXPath("//*[local-name()='frame' or local-name()='iframe']").toList.zipWithIndex
    raw map { case (elem, index) =>
      FrameInfo(index, elem.getAttribute("name"), elem.getAttribute("name"))
    }
  }


  private def getHtml       = d.getPageSource
  private def getScreenshot = d.getScreenshotAs(OutputType.BYTES)
}
