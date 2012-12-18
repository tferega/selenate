package net.selenate
package server
package sessions
package actions

import common.comms._
import req._
import res._

import java.util.ArrayList

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.{ RemoteWebDriver, RemoteWebElement, UselessFileDetector }
import org.openqa.selenium.SearchContext
import org.openqa.selenium.{ By, WebElement }

import scala.collection.JavaConversions._

trait ActionCommons {
  val d: FirefoxDriver

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


  protected object SelenateWebElement {
    def apply(parent: RemoteWebDriver, uuid: String) = {
      val e = new SelenateWebElement
      e.setParent(parent);
      e.setId(uuid);
      e.setFoundBy(parent, "UUID", uuid)
      e.setFileDetector(new UselessFileDetector())
      e
    }
  }
  protected class SelenateWebElement private () extends RemoteWebElement {
    override def setFoundBy(parent: SearchContext, locator: String, term: String) {
      super.setFoundBy(parent, locator, term);
    }
  }

  protected def switchToWindow(windowHandle: String) {
    println("SWITCHING TO DEFAULT CONTENT")
    d.switchTo.defaultContent
    println("SWITCHING TO WINDOW "+ windowHandle)
    d.switchTo.window(windowHandle)
  }

  protected def switchToFrame(windowHandle: String, framePath: Seq[Int]) {
    switchToWindow(windowHandle)
    framePath foreach { e =>
      println("SWITCHING TO FRAME "+ e)
      d.switchTo.frame(e)
    }
  }

  protected def findElementList(method: SeSelectMethod, query: String) = {
    import SeSelectMethod._
    type Selector = String => IndexedSeq[RemoteWebElement]

    def findByBy(byFactory: (String) => By): Selector = { query =>
      d.findElements(byFactory(query)).map(_.asInstanceOf[RemoteWebElement]).toIndexedSeq
    }

    def findByUUID(): Selector = { query =>
      IndexedSeq(SelenateWebElement(d, query))
    }

    val elementFactory = method match {
      case CLASS_NAME        => findByBy(By.className _)
      case CSS_SELECTOR      => findByBy(By.cssSelector _)
      case ID                => findByBy(By.id _)
      case LINK_TEXT         => findByBy(By.linkText _)
      case NAME              => findByBy(By.name _)
      case PARTIAL_LINK_TEXT => findByBy(By.partialLinkText _)
      case TAG_NAME          => findByBy(By.tagName _)
      case UUID              => findByUUID()
      case XPATH             => findByBy(By.xpath _)
    }

    elementFactory(query)
  }

  protected def findElement(method: SeSelectMethod, query: String) = {
    import SeSelectMethod._
    type Selector = String => RemoteWebElement

    def findByBy(byFactory: (String) => By): Selector = { query =>
      d.findElement(byFactory(query)).asInstanceOf[RemoteWebElement]
    }

    def findByUUID(): Selector = { query =>
      SelenateWebElement(d, query)
    }

    val elementFactory = method match {
      case CLASS_NAME        => findByBy(By.className _)
      case CSS_SELECTOR      => findByBy(By.cssSelector _)
      case ID                => findByBy(By.id _)
      case LINK_TEXT         => findByBy(By.linkText _)
      case NAME              => findByBy(By.name _)
      case PARTIAL_LINK_TEXT => findByBy(By.partialLinkText _)
      case TAG_NAME          => findByBy(By.tagName _)
      case UUID              => findByUUID()
      case XPATH             => findByBy(By.xpath _)
    }

    elementFactory(query)
  }

  protected def parseWebElement(framePath: IndexedSeq[Int])(e: RemoteWebElement): SeResElement = {
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
        seqToRealJava(framePath.map(java.lang.Integer.valueOf(_))),
        mapToRealJava(parseAttributeReport(attributeReport)),
        seqToRealJava(Nil))
  }

  protected def parseAttributeReport(reportRaw: Object): Map[String, String] =
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


  protected def inAllFrames[T](f: IndexedSeq[Int] => T): IndexedSeq[T] = {
    type Frame = Int

    def findAllFrames: IndexedSeq[Frame] = {
      val raw = d.findElementsByXPath("//*[local-name()='frame' or local-name()='iframe']").toIndexedSeq.zipWithIndex
      raw map { case (elem, index) => index }
    }

    def inAllFramesDoit(windowHandle: String, framePath: Vector[Int], frame: Frame): IndexedSeq[T] = {
      val fullPath = framePath :+ frame
      switchToFrame(windowHandle, fullPath)

      val result = f
      val childrenResultList = findAllFrames flatMap { f =>
        inAllFramesDoit(windowHandle, fullPath, f)
      }

      childrenResultList :+ f(framePath)
    }

    d.switchTo.defaultContent
    val rootFrames = findAllFrames
    if (rootFrames.isEmpty) {
      IndexedSeq(f(IndexedSeq.empty))
    } else {
      rootFrames.flatMap(inAllFramesDoit(d.getWindowHandle, Vector(), _))
    }
  }
}