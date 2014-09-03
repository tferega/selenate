package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqSwitchFrame
import net.selenate.common.comms.res.SeResSwitchFrame

class SwitchFrameAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqSwitchFrame, SeResSwitchFrame]
    with ActionCommons {
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
