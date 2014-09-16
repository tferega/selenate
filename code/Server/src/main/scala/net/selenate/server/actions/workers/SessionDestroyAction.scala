package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqSessionDestroy
import net.selenate.common.comms.res.SeResSessionDestroy

class SessionDestroyAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqSessionDestroy, SeResSessionDestroy]
    with ActionCommons {
  def act = { arg =>
    new SeResSessionDestroy()
  }
}
