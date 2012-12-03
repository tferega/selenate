package net.selenate
package server
package sessions
package actions

import common.comms.req._

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.{ RemoteWebDriver, RemoteWebElement }
import org.openqa.selenium.SearchContext
import org.openqa.selenium.{ By, WebElement }

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

  protected def findElement(method: SeReqSelectMethod, selector: String) = {
    import SeReqSelectMethod._
    type Selector = String => WebElement

    def findByBy(byFactory: (String) => By): Selector = { selector =>
      d.findElement(byFactory(selector))
    }

    def findByUUID(): Selector = { selector =>
      SelenateWebElement(d, selector)
    }

    val elementFactory = method match {
      case CLASS_NAME        => findByBy(By.className _)
      case CSS_SELECTOR      => findByBy(By.cssSelector _)
      case ID                => findByBy(By.id _)
      case LINK_TEXT         => findByBy(By.linkText _)
      case NAME              => findByBy(By.name _)
      case PARTIAL_LINK_TEXT => findByBy(By.partialLinkText _)
      case TAG_NAME          => findByBy(By.tagName _)
      case UUID              => findByUUID()
      case XPATH             => findByBy(By.xpath _)
    }

    elementFactory(selector)
  }
}