package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqSessionStartKeepalive
import net.selenate.common.comms.res.SeResSessionStartKeepalive

class SessionStartKeepaliveAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqSessionStartKeepalive, SeResSessionStartKeepalive]
    with ActionCommons {
  def act = { arg =>
    new SeResSessionStartKeepalive()
  }
}
