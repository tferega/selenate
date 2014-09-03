package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqDestroySession
import net.selenate.common.comms.res.SeResDestroySession

class DestroySessionAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqDestroySession, SeResDestroySession]
    with ActionCommons {
  def act = { arg =>
    new SeResDestroySession()
  }
}
