package net.selenate.server
package sessions.actions

import net.selenate.common.comms.req.SeReqDeleteCookieNamed
import net.selenate.common.comms.res.SeResDeleteCookieNamed
import org.openqa.selenium.firefox.FirefoxDriver

class DeleteCookieNamedAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends IAction[SeReqDeleteCookieNamed, SeResDeleteCookieNamed]
    with ActionCommons {
  protected val log = Log(classOf[DeleteCookieNamedAction])

  def act = { arg =>
    inAllWindows { address =>
      d.manage.deleteCookieNamed(arg.name)
    }
    new SeResDeleteCookieNamed()
  }
}
