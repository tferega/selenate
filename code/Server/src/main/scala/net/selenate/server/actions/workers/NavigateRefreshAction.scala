package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqNavigateRefresh
import net.selenate.common.comms.res.SeResNavigateRefresh

class NavigateRefreshAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqNavigateRefresh, SeResNavigateRefresh]
    with ActionCommons {
  def act = { SeReqNavigateRefresh =>
    d.navigate.refresh;

    new SeResNavigateRefresh()
  }
}
