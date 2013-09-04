package net.selenate
package server
package sessions
package actions

import common.comms._
import res._
import req._
import org.openqa.selenium.{ Cookie, OutputType, WebElement }
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.firefox.FirefoxDriver
import scala.collection.JavaConversions._
import org.apache.commons.codec.binary.Base64

object CaptureAction {
  private val EmptyPicture = Base64.decodeBase64("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ycnJC74+P/rRj9TY2KiOjg719PSos7NTnZ2dunLlin7xi19Me5ZVVVWl1tZWZWRkaP369UpOTpbP51NjY+Os1+Wb5ohsNpskPdAk/GzEJ9iDwaA6Ozvvezw1NVUTExOP1NgSLMxZXl6eJKmxsVHl5eXKzMyU9NXnqerq6hZ0Xerq6nTr1i19//vf15YtWyRJExMT+uCDD9TZ2anm5mZt2LBh0ucPDg4asXrjjTdktVoViUTkcrkeKFjxS8j4Ad/V1SWn0ymLxfJQz67uHheLxaKDBw/K4XDcF6PpzjgX09g+jkyxWCzGZph/J0+eNHbg5cuXy2azyel0Gh8lSE1NVWlpqXbv3j3pMi5cuKAbN24oFovJ5/PJ7/crMzNTSUlJslgsKi8v19NPPz3t2dHNmzclSUuXLlVBQYGGh4fV0tKiWCymV199VStWrJj0+eFwWO+++658Pp/KysoUDAbldDqNsxWLxaKVK1fqBz/4wTd+4jwajerYsWO6ffu2xsfHjeesXr1aw8PDxif4d+/erW3btk35WuZje1RXV6u2tlZWq1XLli1TTk6O/H6/hoaG1NfXp4KCAr366qsPfWzBGdaiUllZqZSUFJ0/f1537tyR9NXXanJyctTQ0KBAIKCurq4pl9Hf328c0HHxr6WYzWYNDQ1Nux6FhYVyu90Kh8Nyu91yu91KSEhQfn6+Nm7cOGWs4nHZs2ePqqur1dDQIElKSkpSUVGRnE6nwuGwuru7NTExMWmwent7jVht3rxZd+7c0ZdffmlcDu7atWtGn2Gaj+3x4osvym6369NPP1Vra6taW1uNxxwOx5Rnm/M5tuAMa1GKRqMaHByUzWZTWlra/3Rd/H6/PB6PlixZ8kBf7/F4PAqHw3I4HHP+PqPH41EsFlNmZub/5LuRkUhEHo9HXq9XiYmJcjgcs56DWkxjS7AAYJHhqzkACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAgsUmAECwAIBgASBYAECwAIBgASBYAECwAIBgASBYAECwAIBgASBYAECwAIBgASBYAECwAIBgASBYAECwAIBgASBYAECwAIBgASBYAECwAIBgASBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAECwABAsACBYAAgWABAsACBYAAgWABAsACBYAAgWABAsACBYAAgWABAsACBYAAgWABAsACBYAAgWABAsACBYAAgWABAsACBYAAgWABAsACBYAAgWABAsACBYAAgWADwU/wXVVY6msxwIfAAAAABJRU5ErkJggg==".getBytes)

  object XPath {
    val AllChildren = "*"
  }

  object JS {
    val getAttributes = """
var report = [];
var attrList = arguments[0].attributes;
for (var n = 0; n < attrList.length; n++) {
  var entry = attrList[n];
  report.push(entry.name + ' -> ' + entry.value);
};
return report;
"""
  }
}

class CaptureAction(val d: FirefoxDriver)
    extends IAction[SeReqCapture, SeResCapture]
    with ActionCommons {
  import CaptureAction._


  private case class FrameInfo(index: Int, name: String, src: String)

  def act = { arg =>
    new SeResCapture(
        arg.name,
        System.currentTimeMillis,
        setToRealJava(getCookieList),
        seqToRealJava(getWindowList(arg.takeScreenshot)))
  }

  private def getCookieList =
    d.manage.getCookies.toSet map toSelenate

  private def getWindowList(takeScreenshot: Boolean) =
    d.getWindowHandles.toList map { wh => getWindow(wh, takeScreenshot)}

  private def getWindow(windowHandle: String, takeScreenshot: Boolean) = {
    switchToWindow(windowHandle)
    new SeWindow(
        d.getTitle,
        d.getCurrentUrl,
        windowHandle,
        d.manage.window.getPosition.getX,
        d.manage.window.getPosition.getY,
        d.manage.window.getSize.width,
        d.manage.window.getSize.height,
        getHtml,
        getScreenshot(takeScreenshot),
        seqToRealJava(getRootFrames(windowHandle)))
  }

  private def getRootFrames(windowHandle: String): List[SeFrame] =
    findAllFrames map { f =>
      getFrame(windowHandle, Vector.empty, f)
    }

  private def getFrame(windowHandle: String, framePath: Vector[Int], frame: FrameInfo): SeFrame = {
    val fullPath = framePath :+ frame.index
    switchToFrame(windowHandle, fullPath)

    val name       = frame.name
    val src        = frame.src
    val html       = getHtml

    val frameList = findAllFrames map { f =>
      getFrame(windowHandle, fullPath, f)
    }

    new SeFrame(frame.index, name, src, html, windowHandle, seqToRealJava(frameList))
  }

  private implicit def toSelenate(cookie: Cookie): SeCookie = new SeCookie(
    cookie.getDomain,
    cookie.getExpiry,
    cookie.getName,
    cookie.getPath,
    cookie.getValue,
    cookie.isSecure)

  private def findAllFrames: List[FrameInfo] = {
    val raw = d.findElementsByXPath("//*[local-name()='frame' or local-name()='iframe']").toList.zipWithIndex
    raw map { case (elem, index) =>
      FrameInfo(index, elem.getAttribute("name"), elem.getAttribute("name"))
    }
  }


  private def getHtml       = d.getPageSource
  private def getScreenshot(takeScreenshot: Boolean) = takeScreenshot match {
    case true  => d.getScreenshotAs(OutputType.BYTES)
    case false => EmptyPicture
  }
}
