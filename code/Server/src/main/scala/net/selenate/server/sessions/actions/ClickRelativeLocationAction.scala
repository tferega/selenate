package net.selenate.server
package sessions.actions

import linux.LinuxWindow

import extensions.SelenateFirefox
import net.selenate.common.comms.req.SeReqClickRelativeLocation
import net.selenate.common.comms.res.SeResClickRelativeLocation
import org.openqa.selenium.Cookie
import java.util.UUID

class ClickRelativeLocationAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends IAction[SeReqClickRelativeLocation, SeResClickRelativeLocation]
    with ActionCommons {
  protected val log = Log(classOf[ClickRelativeLocationAction])

  def act = { arg =>
    val title = UUID.randomUUID.toString
    d.title = title
    LinuxWindow.clickRelative(d.parentNum, title, arg.x, arg.y)
    new SeResClickRelativeLocation()
  }
}
