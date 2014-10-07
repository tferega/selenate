package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._

import java.io.ByteArrayInputStream
import javax.imageio.ImageIO
import org.openqa.selenium.firefox.FirefoxDriver
import org.sikuli.api.{ DesktopScreenRegion, ImageTarget }
import org.sikuli.api.robot.desktop.DesktopMouse

class WaitForSikuliImageAction(val d: FirefoxDriver) extends IAction[SeReqWaitForSikuliImage, SeResWaitForSikuliImage] {

  protected val log = Log(classOf[WaitForSikuliImageAction])

  def act = { arg =>
    val bais   = new ByteArrayInputStream(arg.image)
    val buffer = ImageIO.read(bais)
    val target = new ImageTarget(buffer)

    val desktop = new DesktopScreenRegion()
    val region  = desktop.wait(target, arg.timeoutMillis)

    new SeResWaitForSikuliImage()
  }
}
