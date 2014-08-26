package net.selenate.server
package sessions.actions

import linux.LinuxWindow

import extensions.SelenateFirefox
import net.selenate.common.comms.req.SeReqSystemInput
import net.selenate.common.comms.res.SeResSystemInput

class SystemInputAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqSystemInput, SeResSystemInput]
    with ActionCommons {
  def act = { arg =>
    LinuxWindow.input(d.displayInfo.map(_.num), arg.input)
    new SeResSystemInput()
  }
}
