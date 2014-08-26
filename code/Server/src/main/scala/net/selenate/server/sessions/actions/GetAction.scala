package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqGet
import net.selenate.common.comms.res.SeResGet

class GetAction(val d: SelenateFirefox)
    extends Action[SeReqGet, SeResGet]
    with ActionCommons {
  def act = { arg =>
    d.get(arg.url)
    new SeResGet()
  }
}
