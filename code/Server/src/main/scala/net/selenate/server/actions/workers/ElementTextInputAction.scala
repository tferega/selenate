package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqElementTextInput
import net.selenate.common.comms.res.SeResElementTextInput
import scala.util.{ Failure, Success, Try }

class ElementTextInputAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqElementTextInput, SeResElementTextInput]
    with ActionCommons {
  def act = { arg =>
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
        throw new IllegalArgumentException(s"An error occurred while executing element text input action ($arg)!", ex)
      case None =>
        throw new IllegalArgumentException(s"An error occurred while executing element text input action ($arg): element not found in any frame!!")
    }
  }
}
