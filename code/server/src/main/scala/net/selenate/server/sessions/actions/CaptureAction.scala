package net.selenate.server
package sessions
package actions

import comms.res._
import comms.req._

import org.openqa.selenium.{ Cookie, OutputType, WebElement }
import org.openqa.selenium.firefox.FirefoxDriver

import scala.collection.JavaConversions._

class CaptureAction(val d: FirefoxDriver) extends IAction[SeReqCapture, SeResCapture] {
  private case class Frame(index: Int, name: String, src: String)

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
    new SeResWindow(
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

  private def getRootFrames(windowHandle: String): List[SeResFrame] =
    findAllFrames map { f =>
      getFrame(windowHandle, Vector.empty, f)
    }

  private def getFrame(windowHandle: String, framePath: Vector[Int], frame: Frame): SeResFrame = {
    val fullPath = framePath :+ frame.index
    switchToFrame(windowHandle, fullPath)

    val name       = frame.name
    val src        = frame.src
    val html       = getHtml
    val screenshot = getScreenshot

    val frameList = findAllFrames map { f =>
      getFrame(windowHandle, fullPath :+ frame.index, f)
    }

    new SeResFrame(frame.index, name, src, html, screenshot, seqToRealJava(frameList))
  }

  private implicit def toSelenate(cookie: Cookie): SeResCookie = new SeResCookie(
    cookie.getDomain,
    cookie.getExpiry,
    cookie.getName,
    cookie.getPath,
    cookie.getValue,
    cookie.isSecure)

  private def switchToWindow(windowHandle: String) {
    println("SWITCHING TO DEFAULT CONTENT")
    d.switchTo.defaultContent
    println("SWITCHING TO WINDOW "+ windowHandle)
    d.switchTo.window(windowHandle)
  }

  private def switchToFrame(windowHandle: String, framePath: Seq[Int]) {
    switchToWindow(windowHandle)
    framePath foreach { e =>
      println("SWITCHING TO FRAME "+ e)
      d.switchTo.frame(e)
    }
  }

  private def findAllFrames: List[Frame] = {
    val raw = d.findElementsByXPath("//*[local-name()='frame' or local-name()='iframe']").toList.zipWithIndex
    raw map { case (elem, index) =>
      Frame(index, elem.getAttribute("name"), elem.getAttribute("name"))
    }
  }


  private def getHtml       = d.getPageSource
  private def getScreenshot = d.getScreenshotAs(OutputType.BYTES)
}