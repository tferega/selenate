package net.selenate.server
package images

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO
import org.sikuli.api.{ DesktopScreenRegion, ImageTarget, ScreenRegion }
import org.sikuli.api.robot.desktop.{ DesktopKeyboard, DesktopMouse }

object SikuliDirect {
  private def getRegion(image: Array[Byte]): Option[ScreenRegion] = {
    val bais   = new ByteArrayInputStream(image)
    val buffer = ImageIO.read(bais)
    val target = new ImageTarget(buffer)

    val desktop = new DesktopScreenRegion()
    val region = desktop.wait(target, 5000)
    Option(region)
  }

  def takeScreenshot(): BufferedImage = {
    val desktop = new DesktopScreenRegion()
    desktop.capture
  }

  def imageExists(image: Array[Byte]) = {
    val region = getRegion(image)
    region.isDefined
  }

  def clickImage(image: Array[Byte]) {
    getRegion(image) match {
      case Some(region) =>
        val loc   = region.getCenter
        val mouse = new DesktopMouse()
        mouse.click(loc)
      case None =>
        throw new RuntimeException("Could not click: image not found")
    }
  }

  def inputText(image: Array[Byte], input: String) {
    clickImage(image)
    val keyboard = new DesktopKeyboard()
    keyboard.`type`(input)
  }
}
