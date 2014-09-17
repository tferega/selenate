package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqCookieAdd
import net.selenate.common.comms.res.SeResCookieAdd
import org.openqa.selenium.Cookie

class CookieAddAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqCookieAdd, SeResCookieAdd]
    with ActionCommons {
  def doAct = { arg =>
    val cookie = arg.getCookie
    inAllWindows { address =>
      val c = new Cookie(
            cookie.getName
          , cookie.getValue
          , cookie.getDomain
          , cookie.getPath
          , cookie.getExpiry
          , cookie.isSecure)
      d.manage.addCookie(c)
    }.force
    new SeResCookieAdd()
  }
}
