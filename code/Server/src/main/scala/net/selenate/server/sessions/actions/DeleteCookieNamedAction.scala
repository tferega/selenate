package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import java.util.ArrayList
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.RemoteWebElement
import scala.collection.JavaConversions._


class DeleteCookieNamedAction(val d: FirefoxDriver)
    extends IAction[SeReqDeleteCookieNamed, SeResDeleteCookieNamed]
    with ActionCommons {

  def act = { arg =>
    inAllWindows { address =>
      d.manage.deleteCookieNamed(arg.name)
    }
    new SeResDeleteCookieNamed()
  }
}
