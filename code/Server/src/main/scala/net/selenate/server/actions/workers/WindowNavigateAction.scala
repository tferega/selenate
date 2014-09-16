package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox
import net.selenate.common.comms.req.SeReqWindowNavigate
import net.selenate.common.comms.res.SeResWindowNavigate
import net.selenate.common.comms.SeNavigateDirection

class WindowNavigateAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqWindowNavigate, SeResWindowNavigate]
    with ActionCommons {
  def act = { arg =>
    import SeNavigateDirection._
    arg.getDirection match {
      case BACK    => d.navigate.back;
      case FORWARD => d.navigate.forward;
      case REFRESH => d.navigate.refresh;
    }

    new SeResWindowNavigate()
  }
}
