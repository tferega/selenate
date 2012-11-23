package net.selenate.server
package sessions
package actions

import comms.res._
import comms.req._

import java.util.ArrayList

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

  private case class Frame(index: Int, name: String, src: String)

  def act = { arg =>
    new SeResCapture(
        arg.name,
        System.currentTimeMillis,
        setToRealJava(getCookieList),
        seqToRealJava(getWindowList))
  }

  private def getEntireDom: SeResElement = {
    val rootElement = d.findElementByXPath("*").asInstanceOf[RemoteWebElement]
    getDomElement(rootElement)
  }

  private def getDomElement(e: RemoteWebElement): SeResElement = {
    val seleniumChildren = e.findElementsByXPath(XPath.AllChildren).map(_.asInstanceOf[RemoteWebElement])
    val children = seleniumChildren map getDomElement

    val attributeReport = d.executeScript(JS.getAttributes, e)
    new SeResElement(
        e.getId,
        e.getLocation.getX,
        e.getLocation.getY,
        e.getSize.getWidth,
        e.getSize.getHeight,
        e.getTagName,
        e.getText,
        e.isDisplayed,
        e.isEnabled,
        e.isSelected,
        parseAttributeReport(attributeReport),
        seqToRealJava(children))
  }

  private def parseAttributeReport(reportRaw: Object): Map[String, String] =
    reportRaw match {
      case report: ArrayList[_] =>
        val attributeList: List[(String, String)] =
          report.toList flatMap {
            case entry: String =>
              val elements = entry.split(" -> ").toList
              elements match {
                case attribute :: value :: Nil =>
                  Some(attribute -> value)
                case _ =>
                  None
              }
            case _ =>
              None
          }
        attributeList.toMap
      case _ =>
        Map.empty
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
        getEntireDom,
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
    val dom        = getEntireDom
    val screenshot = getScreenshot

    val frameList = findAllFrames map { f =>
      getFrame(windowHandle, fullPath :+ frame.index, f)
    }

    new SeResFrame(frame.index, name, src, html, screenshot, dom, seqToRealJava(frameList))
  }

  private implicit def toSelenate(cookie: Cookie): SeResCookie = new SeResCookie(
    cookie.getDomain,
    cookie.getExpiry,
    cookie.getName,
    cookie.getPath,
    cookie.getValue,
    cookie.isSecure)

  private def findAllFrames: List[Frame] = {
    val raw = d.findElementsByXPath("//*[local-name()='frame' or local-name()='iframe']").toList.zipWithIndex
    raw map { case (elem, index) =>
      Frame(index, elem.getAttribute("name"), elem.getAttribute("name"))
    }
  }


  private def getHtml       = d.getPageSource
  private def getScreenshot = d.getScreenshotAs(OutputType.BYTES)
}