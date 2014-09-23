package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqSelectChoose
import net.selenate.common.comms.res.SeResSelectChoose
import net.selenate.common.exceptions.SeActionException
import org.openqa.selenium.support.ui.Select
import scala.util.{ Failure, Success, Try }

class SelectChooseAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqSelectChoose, SeResSelectChoose]
    with ActionCommons {
  def doAct = { arg =>
    val result: Option[Try[Unit]] = elementInAllWindows(arg.getParentSelector) { (address, e) =>
      val s = new Select(e)
      selectOption(s, arg.getOptionSelector)
    }

    result match {
      case Some(Success(())) =>
        new SeResSelectChoose()
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
