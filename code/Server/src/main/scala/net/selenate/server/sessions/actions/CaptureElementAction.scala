package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import java.util.ArrayList
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.RemoteWebElement
import scala.collection.JavaConversions._
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.OutputType
import javax.imageio.ImageIO
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException


class CaptureElementAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends IAction[SeReqCaptureElement, SeResCaptureElement]
    with ActionCommons {

  protected val log = Log(classOf[CaptureElementAction])

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
