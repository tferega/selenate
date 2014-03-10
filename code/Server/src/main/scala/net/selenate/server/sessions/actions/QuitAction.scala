package net.selenate.server
package sessions.actions

import driver.selenium.SelenateFirefox

import net.selenate.common.comms.req.SeReqQuit
import net.selenate.common.comms.res.SeResQuit

class QuitAction(val d: SelenateFirefox) extends IAction[SeReqQuit, SeResQuit] {
  protected val log = Log(classOf[QuitAction])

  def act = { arg =>
    d.quit
    new SeResQuit()
  }
}
