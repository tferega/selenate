package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqStartKeepalive
import net.selenate.common.comms.res.SeResStartKeepalive

class StartKeepaliveAction(val d: SelenateFirefox) extends IAction[SeReqStartKeepalive, SeResStartKeepalive] {
  protected val log = Log(this.getClass)

  def act = { arg =>
    new SeResStartKeepalive()
  }
}
