package net.selenate.server
package sessions.actions

import linux.LinuxWindow

import extensions.SelenateFirefox
import net.selenate.common.comms.req.SeReqSystemInput
import net.selenate.common.comms.res.SeResSystemInput

class SystemInputAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends IAction[SeReqSystemInput, SeResSystemInput]
    with ActionCommons {
  def act = { arg =>
    LinuxWindow.input(d.parentNum, arg.input)
    new SeResSystemInput()
  }
}
