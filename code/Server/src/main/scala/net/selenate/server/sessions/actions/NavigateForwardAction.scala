package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqNavigateForward
import net.selenate.common.comms.res.SeResNavigateForward

class NavigateForwardAction(val d: SelenateFirefox) extends IAction[SeReqNavigateForward, SeResNavigateForward] {
  protected val log = Log(this.getClass)

  def act = { arg =>
    d.navigate.forward;

    new SeResNavigateForward()
  }
}
