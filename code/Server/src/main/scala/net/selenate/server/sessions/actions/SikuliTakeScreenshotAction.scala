package net.selenate.server
package sessions.actions

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import net.selenate.common.comms.req.SeReqSikuliTakeScreenshot
import net.selenate.common.comms.res.SeResSikuliTakeScreenshot
import org.openqa.selenium.firefox.FirefoxDriver
import org.sikuli.api.{ DesktopScreenRegion, ImageTarget, Region }
import net.selenate.server.extensions.SelenateFirefox
import net.selenate.server.Log

class SikuliTakeScreenshotAction (val d: SelenateFirefox) extends IAction[SeReqSikuliTakeScreenshot, SeResSikuliTakeScreenshot] {

  protected val log = Log(classOf[SikuliTakeScreenshotAction])

  def act = { arg =>
    val bais   = new ByteArrayInputStream(arg.image)
    val buffer = ImageIO.read(bais)
    val target = new ImageTarget(buffer)

    val desktop = new DesktopScreenRegion()
    val region  = desktop.wait(target, 5000)

    val location      = region.getUpperLeftCorner()
    val appletRegion  = new DesktopScreenRegion(location.getX(), location.getY(), arg.width, arg.height)
    val capturedImage = appletRegion.capture()

    val baos          = new ByteArrayOutputStream()
    val captureBuffer = ImageIO.write(capturedImage, "png", baos)
    val byteImage     = baos.toByteArray()

    new SeResSikuliTakeScreenshot(byteImage)
  }
}
