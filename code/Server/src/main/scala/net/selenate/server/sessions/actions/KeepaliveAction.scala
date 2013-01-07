package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver

class KeepaliveAction(val d: FirefoxDriver) extends IAction[SeReqKeepalive, SeResKeepalive] {
  def act = { arg =>
    new SeResKeepalive()
  }
}