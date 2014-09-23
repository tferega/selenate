package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqElementClick
import net.selenate.common.comms.res.SeResElementClick
import net.selenate.common.exceptions.SeActionException
import scala.util.{ Failure, Success, Try }

class ElementClickAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends RetryableAction[SeReqElementClick, SeResElementClick]
    with ActionCommons {
  def retryableAct = { arg =>
    val result: Option[Try[Unit]] = elementInAllWindows(arg.getSelector) { (address, e) =>
      e.click
    }

    result match {
      case Some(Success(())) =>
        new SeResElementClick()
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
