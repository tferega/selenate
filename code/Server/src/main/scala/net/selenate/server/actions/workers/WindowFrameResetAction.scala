package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqWindowFrameReset
import net.selenate.common.comms.res.SeResWindowFrameReset

class WindowFrameResetAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqWindowFrameReset, SeResWindowFrameReset]
    with ActionCommons {
  def act = { arg =>
    d.switchTo.defaultContent

    new SeResWindowFrameReset()
  }
}