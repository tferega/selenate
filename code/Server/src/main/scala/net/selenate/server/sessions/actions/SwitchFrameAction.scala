package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqSwitchFrame
import net.selenate.common.comms.res.SeResSwitchFrame

class SwitchFrameAction(val d: SelenateFirefox)
    extends IAction[SeReqSwitchFrame, SeResSwitchFrame]
    with ActionCommons {
  protected val log = Log(this.getClass)

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
