package net.selenate.server
package actions

import net.selenate.common.comms.{ SeElementSelectMethod, SeElementSelector, SeOptionSelectMethod, SeOptionSelector }
import org.openqa.selenium.{ By, StaleElementReferenceException }
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.support.ui.Select
import scala.collection.JavaConversions._
import scala.util.{ Failure, Success, Try }
import net.selenate.common.comms.SeElementVisibility

trait ActionCommons
    extends ActionCommonsBase
    with ActionCommonsFrames
    with ActionCommonsParsers
    with ActionCommonsCerts { self: Loggable =>
  protected def findElementList(selector: SeElementSelector): IndexedSeq[RemoteWebElement] = {
    val by = byFactory(selector)
    d.findElements(by)
      .map(_.asInstanceOf[RemoteWebElement])
      .filter(visibilityFilter(selector.getVisibility))
      .toIndexedSeq
  }

  protected def byFactory(selector: SeElementSelector): By = {
    import SeElementSelectMethod._
    selector.getMethod match {
      case CLASS_NAME        => By.className(selector.getQuery)
      case CSS_SELECTOR      => By.cssSelector(selector.getQuery)
      case ID                => By.id(selector.getQuery)
      case LINK_TEXT         => By.linkText(selector.getQuery)
      case NAME              => By.name(selector.getQuery)
      case PARTIAL_LINK_TEXT => By.partialLinkText(selector.getQuery)
      case TAG_NAME          => By.tagName(selector.getQuery)
      case XPATH             => By.xpath(selector.getQuery)
    }
  }

  protected def visibilityFilter(visibility: SeElementVisibility)(e: RemoteWebElement) = {
    import SeElementVisibility._
    if (visibility != ANY) {
      try {
        (visibility, e.isDisplayed) match {
          case (VISIBLE, false) => false
          case (HIDDEN, true)   => false
          case _                => true
        }
      } catch {
        case _: StaleElementReferenceException => false
      }
    } else {
      true
    }
  }


  protected def selectOption(s: Select, selector: SeOptionSelector) = {
    import SeOptionSelectMethod._
    def selectorFactory = selector.getMethod match {
      case INDEX        => (str: String) => s.selectByIndex(str.toInt)
      case VALUE        => s.selectByValue _
      case VISIBLE_TEXT => s.selectByVisibleText _
    }

    selectorFactory(selector.getQuery)
  }

  protected def elementInAllWindows[T](selector: SeElementSelector)(f: (Address, RemoteWebElement) => T): Option[Try[T]] = {
    val resultIterator = inAllWindows { address =>
      findElementList(selector).toList match {
        case Nil =>
          None
        case e :: _ =>
          val r = Try(f(address, e))
          Some(r)
      }
    }.flatten

    if (resultIterator.hasNext) {
      Some(resultIterator.next)
    } else {
      None
    }
  }

  protected def elementInCache[T](selector: SeElementSelector)(f: (Address, RemoteWebElement) => T): Option[Try[T]] = {
    getFromCache(selector) flatMap { cachedElement =>
      val address = Address(cachedElement.windowHandle, cachedElement.framePath)
      try {
        if (context.useFrames) {
          fullSwitch(cachedElement.windowHandle, cachedElement.framePath)
        }
        Some(Success(f(address, cachedElement.elem)))
      } catch {
        case e: StaleElementReferenceException =>
          val foundElem = findElementList(selector).headOption
          foundElem.map(elem => Try(f(address, elem)))
        case e: Exception =>
          None
      }
    }
  }
}
