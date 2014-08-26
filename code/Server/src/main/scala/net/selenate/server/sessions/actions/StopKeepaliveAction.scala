package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqStopKeepalive
import net.selenate.common.comms.res.SeResStopKeepalive

class StopKeepaliveAction(val d: SelenateFirefox)
    extends Action[SeReqStopKeepalive, SeResStopKeepalive]
    with ActionCommons {
  def act = { arg =>
    new SeResStopKeepalive()
  }
}
