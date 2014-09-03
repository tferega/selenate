package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqDeleteCookieNamed
import net.selenate.common.comms.res.SeResDeleteCookieNamed

class DeleteCookieNamedAction(val sessionID: String, val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqDeleteCookieNamed, SeResDeleteCookieNamed]
    with ActionCommons {
  def act = { arg =>
    inAllWindows { address =>
      d.manage.deleteCookieNamed(arg.name)
    }
    new SeResDeleteCookieNamed()
  }
}
