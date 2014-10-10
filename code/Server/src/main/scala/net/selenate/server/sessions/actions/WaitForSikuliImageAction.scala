package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import java.io.ByteArrayInputStream
import javax.imageio.ImageIO
import net.selenate.common.comms.req.SeReqWaitForSikuliImage
import net.selenate.common.comms.res.SeResWaitForSikuliImage
import org.openqa.selenium.firefox.FirefoxDriver
import org.sikuli.api.{ DesktopScreenRegion, ImageTarget }
import org.sikuli.api.robot.desktop.DesktopMouse

class WaitForSikuliImageAction(val d: SelenateFirefox) extends IAction[SeReqWaitForSikuliImage, SeResWaitForSikuliImage] {

  protected val log = Log(classOf[WaitForSikuliImageAction])

  def act = { arg =>
    val bais   = new ByteArrayInputStream(arg.image)
    val buffer = ImageIO.read(bais)
    val target = new ImageTarget(buffer)

    val desktop = new DesktopScreenRegion()
    val region  = desktop.wait(target, arg.timeoutMillis)

    val isImageFound = region != null

    new SeResWaitForSikuliImage(isImageFound)
  }
}
