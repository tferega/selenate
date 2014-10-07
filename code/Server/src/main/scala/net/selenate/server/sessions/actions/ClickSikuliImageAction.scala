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

class ClickSikuliImageAction(val d: FirefoxDriver) extends IAction[SeReqClickSikuliImage, SeResClickSikuliImage] {

  protected val log = Log(classOf[ClickSikuliImageAction])

  def act = { arg =>
    val bais   = new ByteArrayInputStream(arg.image)
    val buffer = ImageIO.read(bais)
    val target = new ImageTarget(buffer)

    val desktop = new DesktopScreenRegion()
    val region  = desktop.wait(target, arg.timeoutMillis)
    val loc     = region.getCenter

    val mouse = new DesktopMouse()
    mouse.click(loc)

    new SeResClickSikuliImage()
  }
}
