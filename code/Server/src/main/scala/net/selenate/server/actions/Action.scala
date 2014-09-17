package net.selenate.server
package actions

import extensions.SelenateFirefox
import net.selenate.common.comms.req.SeCommsReq
import net.selenate.common.comms.res.SeCommsRes
import net.selenate.common.exceptions.SeActionException

trait Action[A <: SeCommsReq, R <: SeCommsRes] extends Loggable {
  val sessionID: String
  lazy val name = clazz.getSimpleName
  override lazy val logPrefix = Some(sessionID)

  def doAct: (A) => R

  def act: (A) => R = { arg =>
    try {
      doAct(arg)
    } catch {
      case e: Exception =>
        logWarn(s"An error occurred while executing $name!", e)
        throw new SeActionException(name, arg, e)
    }
  }
}
