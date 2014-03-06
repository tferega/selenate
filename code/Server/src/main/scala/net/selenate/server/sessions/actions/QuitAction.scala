package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqQuit
import net.selenate.common.comms.res.SeResQuit
import org.openqa.selenium.firefox.FirefoxDriver

class QuitAction(val d: FirefoxDriver) extends IAction[SeReqQuit, SeResQuit] {
  protected val log = Log(classOf[QuitAction])

  def act = { arg =>
    d.quit
    new SeResQuit()
  }
}
