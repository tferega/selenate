package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqSwitchFrame
import net.selenate.common.comms.res.SeResSwitchFrame
import org.openqa.selenium.firefox.FirefoxDriver

class SwitchFrameAction(val d: FirefoxDriver)
    extends IAction[SeReqSwitchFrame, SeResSwitchFrame]
    with ActionCommons {
  protected val log = Log(classOf[SwitchFrameAction])

  def act = { arg =>
    if(arg.frame != null) {
      d.switchTo.frame(arg.frame)
    }
    else {
      val webElement = findElement(arg.selector.method, arg.selector.query)
      d.switchTo().frame(webElement);
    }

    new SeResSwitchFrame()
  }
}
