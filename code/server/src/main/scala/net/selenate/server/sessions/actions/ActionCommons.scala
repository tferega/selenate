package net.selenate.server
package sessions
package actions

import org.openqa.selenium.firefox.FirefoxDriver

trait ActionCommons {
  val d: FirefoxDriver

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
}