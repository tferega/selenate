package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver

import scala.collection.JavaConversions._

class QuitAction(val d: FirefoxDriver) extends IAction[SeReqQuit, SeResQuit] {

  protected val log = Log(classOf[QuitAction])

  def act = { arg =>
    d.quit

    new SeResQuit()
  }
}
