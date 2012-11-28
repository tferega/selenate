package net.selenate
package server
package sessions
package actions

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.{ RemoteWebDriver, RemoteWebElement }
import org.openqa.selenium.SearchContext

trait ActionCommons {
  val d: FirefoxDriver

  protected object SelenateWebElement {
    def apply(parent: RemoteWebDriver, uuid: String) = {
      val e = new SelenateWebElement
      e.setParent(parent);
      e.setId(uuid);
      e.setFoundBy(parent, "UUID", uuid)
      e
    }
  }
  protected class SelenateWebElement private () extends RemoteWebElement

  protected def switchToWindow(windowHandle: String) {
    println("SWITCHING TO DEFAULT CONTENT")
    d.switchTo.defaultContent
    println("SWITCHING TO WINDOW "+ windowHandle)
    d.switchTo.window(windowHandle)
  }

  protected def switchToFrame(windowHandle: String, framePath: Seq[Int]) {
    switchToWindow(windowHandle)
    framePath foreach { e =>
      println("SWITCHING TO FRAME "+ e)
      d.switchTo.frame(e)
    }
  }

  protected def createWebElement() {

  }
}