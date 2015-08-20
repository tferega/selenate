package net.selenate.server
package sessions.actions

import org.openqa.selenium.firefox.FirefoxDriver
import net.selenate.server.images.SikuliDirect
import net.selenate.common.comms.req.SeReqSikuliImageExists
import net.selenate.common.comms.res.SeResSikuliImageExists

class SikuliImageExistsAction (val d: FirefoxDriver) extends IAction[SeReqSikuliImageExists, SeResSikuliImageExists] {

  protected val log = Log(classOf[SikuliImageExistsAction])

  def act = { arg =>
    val isImageFound = SikuliDirect.imageExists(arg.image)
    new SeResSikuliImageExists(isImageFound)
  }
}
