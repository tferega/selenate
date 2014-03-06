package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqAddCookie
import net.selenate.common.comms.res.SeResAddCookie
import org.openqa.selenium.Cookie
import org.openqa.selenium.firefox.FirefoxDriver

class AddCookieAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends IAction[SeReqAddCookie, SeResAddCookie]
    with ActionCommons {
  protected val log = Log(classOf[AddCookieAction])

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
