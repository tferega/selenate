package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqElementTextInput
import net.selenate.common.comms.res.SeResElementTextInput
import net.selenate.common.exceptions.SeActionException
import scala.util.{ Failure, Success, Try }

class ElementTextInputAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqElementTextInput, SeResElementTextInput]
    with ActionCommons {
  def doAct = { arg =>
    val result: Option[Try[Unit]] = elementInAllWindows(arg.getSelector) { (address, e) =>
      if (!arg.isAppend) {
        e.clear
      }
      e.sendKeys(arg.getText)
    }

    result match {
      case Some(Success(())) =>
        new SeResElementTextInput()
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
