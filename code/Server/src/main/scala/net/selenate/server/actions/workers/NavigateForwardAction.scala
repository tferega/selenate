package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqNavigateForward
import net.selenate.common.comms.res.SeResNavigateForward

class NavigateForwardAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqNavigateForward, SeResNavigateForward]
    with ActionCommons {
  def act = { arg =>
    d.navigate.forward;

    new SeResNavigateForward()
  }
}
