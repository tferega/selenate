package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver

class GetAction(val d: FirefoxDriver) extends IAction[SeReqGet, SeResGet] {

  protected val log = Log(classOf[GetAction])

  def act = { arg =>
    d.get(arg.url)

    new SeResGet()
  }
}
