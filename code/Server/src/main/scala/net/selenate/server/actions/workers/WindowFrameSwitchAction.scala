package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqWindowFrameSwitch
import net.selenate.common.comms.res.SeResWindowFrameSwitch
import scala.collection.JavaConversions._

class WindowFrameSwitchAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqWindowFrameSwitch, SeResWindowFrameSwitch]
    with ActionCommons {
  def doAct = { arg =>
    frameReset()
    arg.getFramePath foreach { f => frameSwitch(f) }

    new SeResWindowFrameSwitch()
  }
}
