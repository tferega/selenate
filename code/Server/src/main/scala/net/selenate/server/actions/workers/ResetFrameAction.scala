package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqResetFrame
import net.selenate.common.comms.res.SeResResetFrame

class ResetFrameAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqResetFrame, SeResResetFrame]
    with ActionCommons {
  def act = { arg =>
    d.switchTo.defaultContent

    new SeResResetFrame()
  }
}
