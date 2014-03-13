package net.selenate.server
package sessions.actions

import linux.LinuxWindow

import extensions.SelenateFirefox
import net.selenate.common.comms.req.SeReqSystemClick
import net.selenate.common.comms.res.SeResSystemClick
import org.openqa.selenium.Cookie
import java.util.UUID

class SystemClickAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends IAction[SeReqSystemClick, SeResSystemClick]
    with ActionCommons {
  protected val log = Log(classOf[SystemClickAction])

  def act = { arg =>
    val title = UUID.randomUUID.toString
    d.title = title
    LinuxWindow.clickRelative(d.parentNum, title, arg.x, arg.y)
    new SeResSystemClick()
  }
}
