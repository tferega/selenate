package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqSessionSetContext
import net.selenate.common.comms.res.SeResSessionSetContext

class SessionSetContextAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqSessionSetContext, SeResSessionSetContext]
    with ActionCommons {
  def doAct = { arg =>
    new SeResSessionSetContext()
  }
}
