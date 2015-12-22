package net.selenate.server
package actions
package workers

import com.ferega.procrun._
import extensions.SelenateFirefox
import java.io.{ ByteArrayInputStream, ByteArrayOutputStream }
import java.nio.file.{ Files, Paths }
import javax.imageio.ImageIO
import net.selenate.common.comms.req.SeReqCaptchaBreak
import net.selenate.common.comms.res.SeResCaptchaBreak
import net.selenate.common.exceptions.SeActionException
import net.selenate.server.linux.LinuxProc
import org.openqa.selenium.OutputType
import org.openqa.selenium.remote.RemoteWebElement
import scala.util.{ Failure, Success, Try }

class CaptchaBreakAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqCaptchaBreak, SeResCaptchaBreak]
    with ActionCommons {
  def doAct = { arg =>
    val result: Option[Try[Array[Byte]]] =
      fromCache(arg) orElse fromFrames(arg)

    result match {
      case Some(Success((r))) =>
        val text = breakCaptcha(r)
        new SeResCaptchaBreak(text)
      case Some(Failure(ex)) =>
        logError(s"An error occurred while executing $name action ($arg)!", ex)
        throw new SeActionException(name, arg, ex)
      case None =>
        val msg = "element not found in any frame"
        logError(s"An error occurred while executing $name action ($arg): $msg!")
        throw new SeActionException(name, arg, msg)
    }
  }

  def fromCache(arg: SeReqCaptchaBreak): Option[Try[Array[Byte]]] =
    elementInCache(arg.getSelector) { (address, elem) =>
      doCapture(elem)
    }

  def fromFrames(arg: SeReqCaptchaBreak): Option[Try[Array[Byte]]] =
    elementInAllWindows(arg.getSelector) { (address, elem) =>
      doCapture(elem)
    }

  def doCapture(elem: RemoteWebElement): Array[Byte] = {
    val screen     = d.getScreenshotAs(OutputType.BYTES)
    val bais       = new ByteArrayInputStream(screen)
    val baos       = new ByteArrayOutputStream()
    val img        = ImageIO.read(bais)
    val dest       = img.getSubimage(elem.getLocation.getX, elem.getLocation.getY, elem.getSize.getWidth, elem.getSize.getHeight)
    ImageIO.write(dest, "png", baos)
    val screenBody = baos.toByteArray()
    baos.close()
    bais.close()
    screenBody
  }

  def breakCaptcha(captcha: Array[Byte]): String = {
    val captchaFile = Files.createTempFile("captcha-", ".png")
    val captchaOut= Files.createTempFile("captcha-", "")
    Files.write(captchaFile, captcha)
    LinuxProc.runAndEnd("tesseract", captchaFile | captchaOut)
    new String(Files.readAllBytes(Paths.get(captchaOut + ".txt")))
  }
}
