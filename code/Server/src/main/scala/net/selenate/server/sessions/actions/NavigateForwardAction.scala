package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqNavigateForward
import net.selenate.common.comms.res.SeResNavigateForward
import org.openqa.selenium.firefox.FirefoxDriver

class NavigateForwardAction(val d: FirefoxDriver) extends IAction[SeReqNavigateForward, SeResNavigateForward] {
  protected val log = Log(classOf[NavigateForwardAction])

  def act = { arg =>
    d.navigate.forward;

    new SeResNavigateForward()
  }
}
