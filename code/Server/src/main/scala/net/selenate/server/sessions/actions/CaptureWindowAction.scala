package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqCaptureWindow
import net.selenate.common.comms.res.SeResCaptureWindow
import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.IOUtils

class CaptureWindowAction(val d: SelenateFirefox)(implicit context: ActionContext)
    extends Action[SeReqCaptureWindow, SeResCaptureWindow]
    with ActionCommons {
  def act = { arg =>
    val resScreenshotList: Stream[Option[SeResCaptureWindow]] = inAllWindows { address =>
      tryo {
        val webElement   = findElement(arg.method, arg.query)
        val baScreenshot = getScreenshot()
        new SeResCaptureWindow(baScreenshot)
      }
    }

    val e = resScreenshotList.flatten
    if (e.isEmpty) {
      throw new IllegalArgumentException("Couldn't take screenshot." +
          "Element [%s, %s] was not found in any frame!".format(arg.method.toString, arg.query))
    } else {
      e(0)
    }
  }

  private def getScreenshot(): Array[Byte] = {

    val html2Canvasplugin = IOUtils.toString(getClass().getResourceAsStream("html2canvas.min.js"))

    logDebug("Injecting html2canvas.min.js...")
    d.executeScript(appendScript(html2Canvasplugin))

    logDebug("Injecting canvas to the body")
    d.executeScript("""html2canvas(document.body, {
        onrendered: function(canvas) {
            document.body.appendChild(canvas);
          }
      });
      """)

    val base64Img = d.executeScript("""return document.getElementsByTagName('canvas')[0].toDataURL();""")

    Base64.decodeBase64(base64Img.toString.replace("data:image/png;base64,", ""))
  }

  private def appendScript(jsplugin: String) = """
    var script = document.createElement(script);
    script.innerHTML = %s;
    document.body.appendChild(script);""" format jsplugin
}
