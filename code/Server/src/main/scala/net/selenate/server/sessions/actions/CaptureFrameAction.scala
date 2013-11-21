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

class CaptureFrameAction(val d: FirefoxDriver)
    extends IAction[SeReqCaptureFrame, SeResCaptureFrame]
    with ActionCommons {

  protected val log = Log(classOf[CaptureFrameAction])

  def act = { arg =>
    val html2Canvasplugin = IOUtils.toString(getClass().getResourceAsStream("html2canvas.min.js"))

    log.debug("Injecting html2canvas.min.js...")
    d.executeScript(appendScript(html2Canvasplugin))

    log.debug("Injecting canvas to the body")
    d.executeScript("""html2canvas(document.body, {
        onrendered: function(canvas) {
            document.body.appendChild(canvas);
          }
      });
      """)

    val base64Img = d.executeScript("""return document.getElementsByTagName('canvas')[0].toDataURL();""")

    new SeResCaptureFrame(Base64.decodeBase64(base64Img.toString.replace("data:image/png;base64,", "")))
  }

  private def appendScript(jsplugin: String) = """
    var script = document.createElement(script);
    script.innerHTML = %s;
    document.body.appendChild(script);""" format jsplugin
}