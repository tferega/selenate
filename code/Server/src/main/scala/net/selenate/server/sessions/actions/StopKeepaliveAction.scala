package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.remote.RemoteWebDriver

class StopKeepaliveAction(val d: RemoteWebDriver) extends IAction[SeReqStopKeepalive, SeResStopKeepalive] {

  protected val log = Log(classOf[StopKeepaliveAction])

  def act = { arg =>
    new SeResStopKeepalive()
  }
}
