package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqSetUseFrames
import net.selenate.common.comms.res.SeResSetUseFrames

class SetUseFramesAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqSetUseFrames, SeResSetUseFrames]
    with ActionCommons {
  def act = { arg =>
    new SeResSetUseFrames()
  }
}
