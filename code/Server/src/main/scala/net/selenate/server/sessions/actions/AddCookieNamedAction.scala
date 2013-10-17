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
import org.openqa.selenium.Cookie


class AddCookieNamedAction(val d: FirefoxDriver)
    extends IAction[SeReqAddCookieNamed, SeResAddCookieNamed]
    with ActionCommons {

  def act = { arg =>
    inAllWindows { address =>
      tryo {
        val c = new Cookie(arg.name, arg.cookie)
        d.manage.addCookie(c)
      }
    }
    new SeResAddCookieNamed()
  }
}
