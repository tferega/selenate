package net.selenate.server
package images

import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

object Images {
  def imageExists(numOpt: Option[Int], image: Array[Byte]): Boolean = {
    numOpt match {
      case Some(num) => SikuliScreen.imageExists(num, image)
      case None      => SikuliDirect.imageExists(image)
    }
  }

  def clickImage(numOpt: Option[Int], image: Array[Byte]) {
    numOpt match {
      case Some(num) => SikuliScreen.clickImage(num, image)
      case None      => SikuliDirect.clickImage(image)
    }
  }

  def takeScreenshot(numOpt: Option[Int]): Array[Byte] = {
    val buffer = numOpt match {
      case Some(num) => SikuliScreen.takeScreenshot(num)
      case None      => SikuliDirect.takeScreenshot()
    }

    val baos = new ByteArrayOutputStream()
    ImageIO.write(buffer, "png", baos)
    baos.flush
    val result = baos.toByteArray
    baos.close
    result
  }

  def inputText(numOpt: Option[Int], image: Array[Byte], text: String) {
    numOpt match {
      case Some(num) => SikuliScreen.inputText(num, image, text)
      case None      => SikuliDirect.inputText(image, text)
    }
  }
}
