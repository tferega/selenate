package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqNavigateRefresh
import net.selenate.common.comms.res.SeResNavigateRefresh

class NavigateRefreshAction(val d: SelenateFirefox)
    extends Action[SeReqNavigateRefresh, SeResNavigateRefresh]
    with ActionCommons {
  def act = { SeReqNavigateRefresh =>
    d.navigate.refresh;

    new SeResNavigateRefresh()
  }
}
