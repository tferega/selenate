package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import net.selenate.common.comms.req.SeReqWindowCapture
import net.selenate.common.comms.res.SeResWindowCapture
import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.IOUtils

object WindowCaptureAction {
  val ErrorPicture      = IOUtils.toByteArray(clazz.getResourceAsStream("errorpic.png"))
  val html2Canvasplugin = IOUtils.toString(getClass().getResourceAsStream("html2canvas.min.js"))
}

class WindowCaptureAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqWindowCapture, SeResWindowCapture]
    with ActionCommons {
  import WindowCaptureAction._

  def doAct = { arg =>
    val screenshot =
      tryWithAlternative("capturing a window", ErrorPicture) {
        if (context.useFrames) {
          windowSwitch(arg.getWindowHandle)
        }
        getScreenshot()
      }

    new SeResWindowCapture(screenshot)
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

  private def tryWithAlternative[T](desc: String, alternative: T)(f: => T): T =
    try {
      f
    } catch {
      case e: Exception =>
        logWarn(s"An error occured while $desc!", e)
        alternative
    }
}
