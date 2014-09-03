package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqElementExists
import net.selenate.common.comms.res.SeResElementExists

class ElementExistsAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqElementExists, SeResElementExists]
    with ActionCommons {
  def act = { arg =>
    val resElementList: Stream[Boolean] = inAllWindows { address =>
      tryb {
        findElement(arg.method, arg.query)
      }
    }

    val isFound = resElementList.contains(true)
    new SeResElementExists(isFound)
  }
}
