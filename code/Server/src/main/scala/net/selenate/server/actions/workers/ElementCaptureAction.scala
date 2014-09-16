package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import java.io.{ ByteArrayInputStream, ByteArrayOutputStream }
import javax.imageio.ImageIO
import net.selenate.common.comms.req.SeReqElementCapture
import net.selenate.common.comms.res.SeResElementCapture
import org.openqa.selenium.OutputType
import scala.util.{ Failure, Success, Try }

class ElementCaptureAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqElementCapture, SeResElementCapture]
    with ActionCommons {
  def act = { arg =>
    val result: Option[Try[Array[Byte]]] =
      elementInAllWindows(arg.getSelector) { (address, e) =>
        val screen     = d.getScreenshotAs(OutputType.BYTES)
        val bais       = new ByteArrayInputStream(screen)
        val baos       = new ByteArrayOutputStream()
        val img        = ImageIO.read(bais)
        val dest       = img.getSubimage(e.getLocation.getX, e.getLocation.getY, e.getSize.getWidth, e.getSize.getHeight)
        ImageIO.write(dest, "png", baos)
        val screenBody = baos.toByteArray()
        baos.close()
        bais.close()
        screenBody
      }

    result match {
      case Some(Success((r))) =>
        new SeResElementCapture(r)
      case Some(Failure(ex)) =>
        throw new IllegalArgumentException(s"An error occurred while executing element capture action ($arg)!", ex)
      case None =>
        throw new IllegalArgumentException(s"An error occurred while executing element capture action ($arg): element not found in any frame!!")
    }
  }
}
