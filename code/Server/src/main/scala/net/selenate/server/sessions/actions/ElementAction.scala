package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import java.util.ArrayList

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.RemoteWebElement

import scala.collection.JavaConversions._


object ElementAction {
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

class ElementAction(val d: FirefoxDriver)
    extends IAction[SeReqElement, SeResElement]
    with ActionCommons {
  import ElementAction._

  def act = { arg =>
    val e = findElement(arg.method, arg.selector).asInstanceOf[RemoteWebElement]
    parseWebElement(e)
  }


  private def parseWebElement(e: RemoteWebElement): SeResElement = {
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
        mapToRealJava(parseAttributeReport(attributeReport)),
        seqToRealJava(Nil))
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

}