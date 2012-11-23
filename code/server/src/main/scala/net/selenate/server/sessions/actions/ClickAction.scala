package net.selenate.server
package sessions
package actions

import comms.res._
import comms.req._

import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.{ By, WebElement }

class ClickAction(val d: FirefoxDriver)
    extends IAction[SeReqClick, SeResClick]
    with ActionCommons {
  import SeReqSelectMethod._
  type Selector = String => WebElement

  def act = { arg =>
    val elementFactory = arg.method match {
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

    val e = elementFactory(arg.selector)
    e.click

    new SeResClick()
  }


  def findByBy(byFactory: (String) => By): Selector = { selector =>
    d.findElement(byFactory(selector))
  }

  def findByUUID(): Selector = { selector =>
    SelenateWebElement(d, selector)
  }
}