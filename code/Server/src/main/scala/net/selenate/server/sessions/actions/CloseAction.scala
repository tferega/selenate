package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqClose
import net.selenate.common.comms.res.SeResClose
import org.openqa.selenium.firefox.FirefoxDriver

class CloseAction(val d: FirefoxDriver)
    extends IAction[SeReqClose, SeResClose]
    with ActionCommons {
  protected val log = Log(classOf[CloseAction])

  def act = { arg =>
    switchToWindow(arg.windowHandle)
    d.close

    new SeResClose()
  }
}
