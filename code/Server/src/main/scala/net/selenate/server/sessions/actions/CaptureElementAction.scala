package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import java.io.{ ByteArrayInputStream, ByteArrayOutputStream, IOException }
import javax.imageio.ImageIO
import net.selenate.common.comms.req.SeReqCaptureElement
import net.selenate.common.comms.res.SeResCaptureElement
import org.openqa.selenium.OutputType
import scala.collection.JavaConversions._

class CaptureElementAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends IAction[SeReqCaptureElement, SeResCaptureElement]
    with ActionCommons {
  def act = { arg =>
    try {
      switchToFrame(arg.windowHandle, arg.framePath.map(_.toInt).toIndexedSeq)
      val elem       = findElement(arg.method, arg.query)
      val screen     = d.getScreenshotAs(OutputType.BYTES)
      val bais       = new ByteArrayInputStream(screen)
      val baos       = new ByteArrayOutputStream()
      val width      = elem.getSize.getWidth
      val height     = elem.getSize.getHeight
      val img        = ImageIO.read(bais)
      val dest       = img.getSubimage(elem.getLocation.getX, elem.getLocation.getY, width, height)
      ImageIO.write(dest, "png", baos)
      val res        = baos.toByteArray()
      baos.close()
      bais.close()

      new SeResCaptureElement(res)
    } catch {
      case e: Exception => throw new IOException("An error occured while capturing element (%s, %s)".format(arg.method, arg.query), e)
    }
  }
}
