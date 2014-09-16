package net.selenate.server.actions

import org.openqa.selenium.WebElement

object FrameInfo {
  def parse(arg: (WebElement, Int)) {
    val element = arg._1
    val index   = arg._2
    FrameInfo(index, element.getAttribute("name"), element.getAttribute("src"))
  }
}

case class FrameInfo(index: Int, name: String, src: String)
