package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import org.openqa.selenium.firefox.FirefoxDriver
import scala.collection.JavaConversions._

class NavigateBackAction(val d: FirefoxDriver) extends IAction[SeReqNavigateBack, SeResNavigateBack] {
  protected val log = Log(classOf[NavigateBackAction])

  def act = { arg =>
    d.navigate.back;

    new SeResNavigateBack()
  }
}
