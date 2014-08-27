package net.selenate.server
package sessions.actions

import linux.LinuxWindow
import extensions.SelenateFirefox
import net.selenate.common.comms.req.SeReqSystemClick
import net.selenate.common.comms.res.SeResSystemClick
import java.util.UUID
import net.selenate.common.util.NamedUUID

class SystemClickAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqSystemClick, SeResSystemClick]
    with ActionCommons {
  private val uuidFactory = new NamedUUID("Title")

  def act = { arg =>
    val title = uuidFactory.random
    d.title = title
    LinuxWindow.clickRelative(d.displayInfo.map(_.num), title, arg.x, arg.y)
    new SeResSystemClick()
  }
}
