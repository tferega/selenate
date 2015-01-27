package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver

class SetConfigureS3ClientAction(val d: FirefoxDriver) extends IAction[SeReqConfigureS3Client, SeResConfigureS3Client] {

  protected val log = Log(classOf[SetConfigureS3ClientAction])

  def act = { arg =>
    new SeResConfigureS3Client()
  }
}
