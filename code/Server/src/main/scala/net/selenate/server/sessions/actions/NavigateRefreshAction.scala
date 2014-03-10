package net.selenate.server
package sessions.actions

import driver.selenium.SelenateFirefox

import net.selenate.common.comms.req.SeReqNavigateRefresh
import net.selenate.common.comms.res.SeResNavigateRefresh

class NavigateRefreshAction(val d: SelenateFirefox) extends IAction[SeReqNavigateRefresh, SeResNavigateRefresh] {
  protected val log = Log(classOf[NavigateRefreshAction])

  def act = { SeReqNavigateRefresh =>
    d.navigate.refresh;

    new SeResNavigateRefresh()
  }
}
