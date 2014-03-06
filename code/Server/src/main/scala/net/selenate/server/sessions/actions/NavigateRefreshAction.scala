package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqNavigateRefresh
import net.selenate.common.comms.res.SeResNavigateRefresh
import org.openqa.selenium.firefox.FirefoxDriver

class NavigateRefreshAction(val d: FirefoxDriver) extends IAction[SeReqNavigateRefresh, SeResNavigateRefresh] {
  protected val log = Log(classOf[NavigateRefreshAction])

  def act = { SeReqNavigateRefresh =>
    d.navigate.refresh;

    new SeResNavigateRefresh()
  }
}
