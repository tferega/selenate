package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqGet
import net.selenate.common.comms.res.SeResGet

class GetAction(val d: SelenateFirefox) extends IAction[SeReqGet, SeResGet] {
  protected val log = Log(this.getClass)

  def act = { arg =>
    d.get(arg.url)
    new SeResGet()
  }
}
