package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqGet
import net.selenate.common.comms.res.SeResGet

class GetAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqGet, SeResGet]
    with ActionCommons {
  def act = { arg =>
    d.get(arg.url)
    new SeResGet()
  }
}
