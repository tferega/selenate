package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox
import net.selenate.common.comms.req.SeReqQuit
import net.selenate.common.comms.res.SeResQuit
import net.selenate.server.linux.LinuxDisplay

class QuitAction(val d: SelenateFirefox) extends IAction[SeReqQuit, SeResQuit] {
  protected val log = Log(classOf[QuitAction])

  def act = { arg =>
    d.quit
    d.parentNum match {
      case Some(x) => LinuxDisplay.destroy(x)
      case _ =>
    }
    new SeResQuit()
  }
}
