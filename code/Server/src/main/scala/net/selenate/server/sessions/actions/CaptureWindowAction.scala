package net.selenate
package server
package sessions
package actions

import common.comms._
import res._
import req._
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.{ By, WebElement }
import org.apache.commons.codec.binary.Base64
import org.apache.commons.io._
import org.openqa.selenium.remote.RemoteWebElement
import net.selenate.server.util.TagSoupCleaner

class CaptureWindowAction(val d: FirefoxDriver)(implicit context: ActionContext)
    extends IAction[SeReqCaptureWindow, SeResCaptureWindow]
    with ActionCommons
    with WaitFor {

  protected val log = Log(classOf[CaptureWindowAction])

  def act = { arg =>
    val resFindElement = (inAllWindows { address => // set us in the right frame
      tryo {
        if(elementExists(arg.method, arg.query).isDefined)
          Some(getScreenshot(arg.cssElement))
        else None
      }
    }).flatten
    
    resFindElement.head match {
      case Some(scr) => 
        new SeResCaptureWindow(scr)
      case _ =>
        throw new IllegalArgumentException("Couldn't take screenshot." +
          "Element [%s, %s] was not found in any frame!".format(arg.method.toString, arg.query))
    }
  }

  private def getScreenshot(cssElement: String): Array[Byte] = {
    val html2Canvasplugin = IOUtils.toString(getClass().getResourceAsStream("html2canvas.min.js"))

    val (captureElement, cssElementID) = cssElement.isEmpty() match {
      case true =>
        val cssElemID   = "captureScreenshot"
        val captureElem = "document.body"
        d.executeScript(createImageForWholePage(html2Canvasplugin, captureElem, cssElemID))
        (captureElem, cssElemID)
      case _    =>
        val withAllTags = TagSoupCleaner.fromString(d.executeScript(getElementHtml(cssElement)).toString()).replaceAll("\n", "").replaceAll("'", "\\\\'")
        val noStartTags = withAllTags.substring(49, withAllTags.length())
        val html        = noStartTags.substring(0, noStartTags.length() - 14)
        val cssElemID   = "%s" format cssElement.replaceAll("\\W", "")
        d.executeScript(createImageForElement(cssElement, cssElemID, html))
        ("document.querySelector('%s')" format cssElement, cssElemID)
    }

    val canvasID = "canvas#%s" format cssElementID

    val image = waitForElement(SeElementSelectMethod.CSS_SELECTOR, canvasID) match {
      case Some(x) =>
        val base64Img = d.executeScript(retrieveCanvasData(cssElementID))
        Base64.decodeBase64(base64Img.toString.replace("data:image/png;base64,", ""))
      case _       =>
        throw new IllegalArgumentException("Couldn't get data from image." +
        "Canvas [%s] was not found in this frame! Error was thrown while waiting for element".format(canvasID))
    }

    d.executeScript(removeCanvas(canvasID))
    image
  }

  private def getElementHtml(cssElement: String) = """
    return document.querySelector('%s').outerHTML;""" format cssElement

  private def createImageForElement(cssSelector: String, id: String, html: String) = """
    function createImage(cssSelector, id, html) {
        var w = document.querySelector(cssSelector).offsetWidth.toString();
        var h = document.querySelector(cssSelector).offsetHeight.toString();

        var canvas    = document.createElement("canvas");
        canvas.id     = id;
        canvas.width  = w;
        canvas.height = h;
        document.getElementsByTagName('body')[0].appendChild(canvas);
        canvas = document.getElementById(id);

        var ctx  = canvas.getContext('2d');
				var data = '<svg xmlns="http://www.w3.org/2000/svg" width="' + w + '" height="' + h + '">' +
											'<rect width="100%%" height="100%%" fill="white"></rect>' + 
                      '<foreignObject width="100%%" height="100%%">' +
                        '<div xmlns="http://www.w3.org/1999/xhtml" >' + html + '</div>' +
                      '</foreignObject>' +
                    '</svg>';
        var DOMURL = window.URL || window.webkitURL || window;

        var img = new Image();
        var svg = new Blob([data], {
            type: 'image/svg+xml;charset=utf-8'
        });
        var url = DOMURL.createObjectURL(svg);

        img.onload = function () {
            ctx.drawImage(img, 0, 0);
            DOMURL.revokeObjectURL(canvas.toDataURL("image/png"));
        }
        img.src = url;
    }
    createImage("%s", "%s", '%s');""" format (cssSelector, id, html)

  private def createImageForWholePage(jsplugin: String, queryCssElement: String, cssElementID: String) = """
    %s
    html2canvas(%s, {
        onrendered: function(canvas) {
            console.log(canvas);
            canvas.id = "%s";
            document.body.appendChild(canvas);
          }
      });""" format (jsplugin, queryCssElement, cssElementID)

  private def retrieveCanvasData(cssElementID: String) = """
    return document.querySelector('canvas#%s').toDataURL();""" format cssElementID

  private def removeCanvas(canvasID: String) = """
    var element = document.querySelector("%s");
    element.parentNode.removeChild(element);""" format canvasID

  def waitForElement(method: SeElementSelectMethod, query: String): Option[RemoteWebElement] =
    waitForPredicate {
      elementExists(method, query)
    }

  def elementExists(method: SeElementSelectMethod, query: String): Option[RemoteWebElement] =
    try {
      tryo {
        findElement(method, query)
      }
    } catch {
      case e: Exception => None
    }
}