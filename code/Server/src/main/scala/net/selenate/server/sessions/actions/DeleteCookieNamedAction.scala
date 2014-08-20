package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqDeleteCookieNamed
import net.selenate.common.comms.res.SeResDeleteCookieNamed

class DeleteCookieNamedAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends IAction[SeReqDeleteCookieNamed, SeResDeleteCookieNamed]
    with ActionCommons {
  protected val log = Log(this.getClass)

  def act = { arg =>
    inAllWindows { address =>
      d.manage.deleteCookieNamed(arg.name)
    }
    new SeResDeleteCookieNamed()
  }
}
