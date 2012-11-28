package net.selenate.server
package sessions
package actions

import comms.res._
import comms.req._

import org.openqa.selenium.firefox.FirefoxDriver

import scala.collection.JavaConversions._

class QuitAction(val d: FirefoxDriver) extends IAction[SeReqQuit, SeResQuit] {
  def act = { arg =>
    d.quit

    new SeResQuit()
  }
}