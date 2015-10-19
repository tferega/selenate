package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox
import net.selenate.common.comms.req.SeReqElementGetAttributes
import net.selenate.common.comms.res.SeResElementGetAttributes
import net.selenate.common.exceptions.SeActionException
import scala.util.{ Failure, Success, Try }
import java.util.ArrayList
import scala.collection.JavaConversions._

object ElementGetAttributesAction {
  val getAttributesJS = """
      |var report = [];
      |var attrList = arguments[0].attributes;
      |for (var n = 0; n < attrList.length; n++) {
      |  var entry = attrList[n];
      |  report.push(entry.name + ' -> ' + entry.value);
      |};
      |return report;""".stripMargin

  def parseAttributeReport(reportRaw: Object): Map[String, String] = {
    reportRaw match {
      case report: ArrayList[_] =>
        val attributeList: List[(String, String)] =
          report.toList flatMap {
            case entry: String =>
              val elements = entry.split(" -> ").toList
              elements match {
                case attribute :: value :: Nil => Some(attribute -> value)
                case _ => None
              }
            case _ => None
          }
        attributeList.toMap
      case _ => Map.empty
    }
  }
}

class ElementGetAttributesAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends RetryableAction[SeReqElementGetAttributes, SeResElementGetAttributes]
    with ActionCommons {
  import ElementGetAttributesAction._

  def retryableAct = { arg =>
    val result: Option[Try[Map[String, String]]] = elementInAllWindows(arg.getSelector) { (address, e) =>
      val attributeReport = d.executeScript(getAttributesJS, e)
      parseAttributeReport(attributeReport)
    }

    result match {
      case Some(Success((attributeMap))) =>
        new SeResElementGetAttributes(mapToRealJava(attributeMap));
      case Some(Failure(ex)) =>
        logError(s"An error occurred while executing $name action ($arg)!", ex)
        throw new SeActionException(name, arg, ex)
      case None =>
        val msg = "element not found in any frame"
        logError(s"An error occurred while executing $name action ($arg): $msg!")
        throw new SeActionException(name, arg, msg)
    }
  }
}
