package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqCookieDeleteNamed
import net.selenate.common.comms.res.SeResCookieDeleteNamed

class CookieDeleteNamedAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqCookieDeleteNamed, SeResCookieDeleteNamed]
    with ActionCommons {
  def doAct = { arg =>
    inAllWindows { address =>
      d.manage.deleteCookieNamed(arg.getName)
    }.force
    new SeResCookieDeleteNamed()
  }
}
