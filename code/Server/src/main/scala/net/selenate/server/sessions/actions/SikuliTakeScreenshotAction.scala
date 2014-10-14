package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox
import images.Images

import net.selenate.common.comms.req.SeReqSikuliTakeScreenshot
import net.selenate.common.comms.res.SeResSikuliTakeScreenshot

class SikuliTakeScreenshotAction (val d: SelenateFirefox) extends IAction[SeReqSikuliTakeScreenshot, SeResSikuliTakeScreenshot] {

  protected val log = Log(classOf[SikuliTakeScreenshotAction])

  def act = { arg =>
    val result = Images.takeScreenshot(d.parentNum)
    new SeResSikuliTakeScreenshot(result)
  }
}
