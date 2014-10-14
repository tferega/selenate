package net.selenate.server
package images

import linux.LinuxProc

import com.ferega.procrun._
import java.awt.image.BufferedImage
import java.io.{ ByteArrayInputStream, File }
import javax.imageio.ImageIO
import net.selenate.server.sessions.actions.WaitFor
import org.sikuli.api.{ ImageTarget, ScreenRegion, StaticImageScreenRegion }

object SikuliScreen {
  private val waiter = new WaitFor {
    val timeout = 5000
  }

  private def getRegion(screen: BufferedImage, image: Array[Byte]): Option[ScreenRegion] = {
    val bais   = new ByteArrayInputStream(image)
    val buffer = ImageIO.read(bais)
    val target = new ImageTarget(buffer)

    val desktop = new StaticImageScreenRegion(screen)
    val region =  desktop.find(target)
    Option(region)
  }

  def takeScreenshot(num: Int): BufferedImage = {
    val screenshotFile = File.createTempFile(s"$num-screenshot", ".png")
    LinuxProc.runAndEnd("import", "-window" | "root" | screenshotFile, Some(num))
    ImageIO.read(screenshotFile)
  }

  private def waitForRegion(num: Int, image: Array[Byte]): Option[ScreenRegion] = {
    waiter.waitForPredicate {
      val screenshot = takeScreenshot(num)
      getRegion(screenshot, image)
    }
  }

  def imageExists(num: Int, image: Array[Byte]): Boolean = {
    val region = waitForRegion(num, image)
    region.isDefined
  }

  def clickImage(num: Int, image: Array[Byte]) {
    waitForRegion(num, image) match {
      case Some(region) =>
        val loc   = region.getCenter
        LinuxProc.runAndEnd("xdotool", "mousemove" | loc.getX | loc.getY, Some(num))
        LinuxProc.runAndEnd("xdotool", "click" | 1, Some(num))
      case None =>
        throw new RuntimeException("Could not click: image not found")
    }
  }

  def inputText(num: Int, image: Array[Byte], input: String) {
    clickImage(num, image)
    val preparedInput = input.toSeq
    LinuxProc.runAndEnd("xdotool", Seq("key") ++ input, Some(num))
  }
}
