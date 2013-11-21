package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import org.openqa.selenium.firefox.FirefoxDriver
import scala.collection.JavaConversions._

class NavigateForwardAction(val d: FirefoxDriver) extends IAction[SeReqNavigateForward, SeResNavigateForward] {

  protected val log = Log(classOf[NavigateForwardAction])

  def act = { arg =>
    d.navigate.forward;

    new SeResNavigateForward()
  }
}
