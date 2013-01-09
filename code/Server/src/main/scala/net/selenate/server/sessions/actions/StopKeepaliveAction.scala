package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver

class StopKeepaliveAction(val d: FirefoxDriver) extends IAction[SeReqStopKeepalive, SeResStopKeepalive] {
  def act = { arg =>
    new SeResStopKeepalive()
  }
}