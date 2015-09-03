package net.selenate.server
package sessions.actions

import org.openqa.selenium.remote.RemoteWebDriver
import net.selenate.server.images.SikuliDirect

import net.selenate.common.comms.req.SeReqSikuliTakeScreenshot
import net.selenate.common.comms.res.SeResSikuliTakeScreenshot

class SikuliTakeScreenshotAction (val d: RemoteWebDriver) extends IAction[SeReqSikuliTakeScreenshot, SeResSikuliTakeScreenshot] {

  protected val log = Log(classOf[SikuliTakeScreenshotAction])

  def act = { arg =>
    val result = SikuliDirect.takeScreenshot(arg.image, arg.width, arg.height)
    new SeResSikuliTakeScreenshot(result)
  }
}
