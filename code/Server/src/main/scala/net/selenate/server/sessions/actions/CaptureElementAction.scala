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


class CaptureElementAction(val d: FirefoxDriver)
    extends IAction[SeReqCaptureElement, SeResCaptureElement]
    with ActionCommons {

  def act = { arg =>
    val resImageList: Stream[Option[SeResCaptureElement]] = inAllWindows { address =>
      tryo {
        val webElement = findElement(arg.method, arg.query)
        val screen     = d.asInstanceOf[TakesScreenshot].getScreenshotAs(OutputType.BYTES)
        val bais       = new ByteArrayInputStream(screen)
        val baos       = new ByteArrayOutputStream()
        val width      = webElement.getSize.getWidth
        val height     = webElement.getSize.getHeight
        val img        = ImageIO.read(bais)
        val dest       = img.getSubimage(webElement.getLocation.getX, webElement.getLocation.getY, width, height)
        ImageIO.write(dest, "png", baos)
        val res        = baos.toByteArray()
        baos.close()
        bais.close()
        new SeResCaptureElement(res)
      }
    }

    val e = resImageList.flatten
    if (e.isEmpty) {
      throw new IllegalArgumentException("Element [%s, %s] was not found in any frame!".format(arg.method.toString, arg.query))
    } else {
      e(0)
    }
  }
}
