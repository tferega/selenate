package net.selenate.server
package sessions.actions

import driver.selenium.SelenateFirefox

import net.selenate.common.comms.req.SeReqNavigateBack
import net.selenate.common.comms.res.SeResNavigateBack

class NavigateBackAction(val d: SelenateFirefox) extends IAction[SeReqNavigateBack, SeResNavigateBack] {
  protected val log = Log(classOf[NavigateBackAction])

  def act = { arg =>
    d.navigate.back;

    new SeResNavigateBack()
  }
}
