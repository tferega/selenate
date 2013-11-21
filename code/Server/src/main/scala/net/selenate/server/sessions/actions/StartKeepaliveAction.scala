package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver

class StartKeepaliveAction(val d: FirefoxDriver) extends IAction[SeReqStartKeepalive, SeResStartKeepalive] {

  protected val log = Log(classOf[StartKeepaliveAction])

  def act = { arg =>
    new SeResStartKeepalive()
  }
}
