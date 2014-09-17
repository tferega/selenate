package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqSessionStopKeepalive
import net.selenate.common.comms.res.SeResSessionStopKeepalive

class SessionStopKeepaliveAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqSessionStopKeepalive, SeResSessionStopKeepalive]
    with ActionCommons {
  def doAct = { arg =>
    new SeResSessionStopKeepalive()
  }
}
