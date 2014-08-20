package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqAddCookie
import net.selenate.common.comms.res.SeResAddCookie
import org.openqa.selenium.Cookie

class AddCookieAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends IAction[SeReqAddCookie, SeResAddCookie]
    with ActionCommons {
  protected val log = Log(this.getClass)

  def act = { arg =>
    val cookie = arg.cookie
    inAllWindows { address =>
      // convert from selenate cookie to selenium cookie
      val c = new Cookie(
            cookie.getName
          , cookie.getValue
          , cookie.getDomain
          , cookie.getPath
          , cookie.getExpiry
          , cookie.isSecure
          )
      d.manage.addCookie(c)
    }
    new SeResAddCookie()
  }
}
