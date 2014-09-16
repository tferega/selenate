package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox
import linux.LinuxWindow

import net.selenate.common.comms.req.SeReqSystemInput
import net.selenate.common.comms.res.SeResSystemInput

class SystemInputAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqSystemInput, SeResSystemInput]
    with ActionCommons {
  def act = { arg =>
    LinuxWindow.input(d.displayInfo.map(_.num), arg.getInput)
    new SeResSystemInput()
  }
}
