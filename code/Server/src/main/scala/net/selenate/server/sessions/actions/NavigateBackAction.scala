package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqNavigateBack
import net.selenate.common.comms.res.SeResNavigateBack
import org.openqa.selenium.firefox.FirefoxDriver

class NavigateBackAction(val d: FirefoxDriver) extends IAction[SeReqNavigateBack, SeResNavigateBack] {
  protected val log = Log(classOf[NavigateBackAction])

  def act = { arg =>
    d.navigate.back;

    new SeResNavigateBack()
  }
}
