package net.selenate.server
package sessions.actions

import linux.LinuxWindow

import extensions.SelenateFirefox
import net.selenate.common.comms.req.SeReqSystemClick
import net.selenate.common.comms.res.SeResSystemClick
import java.util.UUID

class SystemClickAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqSystemClick, SeResSystemClick]
    with ActionCommons {
  def act = { arg =>
    val title = UUID.randomUUID.toString
    d.title = title
    LinuxWindow.clickRelative(d.parentNum, title, arg.x, arg.y)
    new SeResSystemClick()
  }
}
