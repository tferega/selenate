package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import java.io.ByteArrayInputStream
import javax.imageio.ImageIO
import net.selenate.common.comms.req.SeReqClickSikuliImage
import net.selenate.common.comms.res.SeResClickSikuliImage
import org.openqa.selenium.firefox.FirefoxDriver
import org.sikuli.api.{ DesktopScreenRegion, ImageTarget }
import org.sikuli.api.robot.desktop.DesktopMouse

class ClickSikuliImageAction(val d: SelenateFirefox) extends IAction[SeReqClickSikuliImage, SeResClickSikuliImage] {

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
