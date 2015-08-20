package net.selenate.server
package images

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO
import org.sikuli.api.{ DesktopScreenRegion, ImageTarget, ScreenRegion }
import org.sikuli.api.robot.desktop.{ DesktopKeyboard, DesktopMouse }
import java.io.ByteArrayOutputStream

object SikuliDirect {
  private def getRegion(image: Array[Byte]): Option[ScreenRegion] = {
    val bais   = new ByteArrayInputStream(image)
    val buffer = ImageIO.read(bais)
    val target = new ImageTarget(buffer)

    val desktop = new DesktopScreenRegion()
    val region = desktop.wait(target, 5000)
    Option(region)
  }

  def takeScreenshot(): Array[Byte] = {
    val desktop = new DesktopScreenRegion()
    val buffer  = desktop.capture

    val baos = new ByteArrayOutputStream()
    ImageIO.write(buffer, "png", baos)
    baos.flush
    val result = baos.toByteArray
    baos.close
    result
  }

  def takeScreenshot(image: Array[Byte], width: Int, height: Int): Array[Byte] = {
    val optRegion = getRegion(image).get
    val location  = optRegion.getUpperLeftCorner()

    val cropetLocation = new DesktopScreenRegion(location.getX(), location.getY(), width, height)
    val capturedImage  = cropetLocation.capture()

    val baos = new ByteArrayOutputStream()
    ImageIO.write(capturedImage, "png", baos)
    baos.flush
    val result = baos.toByteArray
    baos.close
    result
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
