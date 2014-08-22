package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqResetFrame
import net.selenate.common.comms.res.SeResResetFrame

class ResetFrameAction(val d: SelenateFirefox)
    extends IAction[SeReqResetFrame, SeResResetFrame]
    with ActionCommons {
  def act = { arg =>
    d.switchTo.defaultContent

    new SeResResetFrame()
  }
}
