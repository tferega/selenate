package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqStopKeepalive
import net.selenate.common.comms.res.SeResStopKeepalive

class StopKeepaliveAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqStopKeepalive, SeResStopKeepalive]
    with ActionCommons {
  def act = { arg =>
    new SeResStopKeepalive()
  }
}
