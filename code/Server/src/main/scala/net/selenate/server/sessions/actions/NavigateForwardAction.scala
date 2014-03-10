package net.selenate.server
package sessions.actions

import driver.selenium.SelenateFirefox

import net.selenate.common.comms.req.SeReqNavigateForward
import net.selenate.common.comms.res.SeResNavigateForward

class NavigateForwardAction(val d: SelenateFirefox) extends IAction[SeReqNavigateForward, SeResNavigateForward] {
  protected val log = Log(classOf[NavigateForwardAction])

  def act = { arg =>
    d.navigate.forward;

    new SeResNavigateForward()
  }
}
