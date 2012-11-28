package net.selenate.server
package sessions
package actions

import comms.res._
import comms.req._

import org.openqa.selenium.firefox.FirefoxDriver

class GetAction(val d: FirefoxDriver) extends IAction[SeReqGet, SeResGet] {
  def act = { arg =>
    d.get(arg.url)

    new SeResGet()
  }
}