package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqStartKeepalive
import net.selenate.common.comms.res.SeResStartKeepalive

class StartKeepaliveAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqStartKeepalive, SeResStartKeepalive]
    with ActionCommons {
  def act = { arg =>
    new SeResStartKeepalive()
  }
}
