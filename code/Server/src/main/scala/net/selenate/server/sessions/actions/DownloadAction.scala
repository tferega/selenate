package net.selenate
package server
package sessions
package actions

import common.comms.res._
import common.comms.req._
import dispatch._
import com.ning.http.client.Cookie
import java.{ util => ju }
import org.openqa.selenium.firefox.FirefoxDriver
import scala.collection.JavaConversions._
import java.io.IOException

class DownloadAction(val d: FirefoxDriver) extends IAction[SeReqDownload, SeResDownload] {
  def act = { arg =>
    val request = url(arg.url)
    request.setHeader("Referer", d.getCurrentUrl)
    request.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:12.0) Gecko/20100101 Firefox/12.0")
    getCookies foreach request.addCookie

    val body =
      Http(request OK as.Bytes).either.apply match {
        case Left(e) =>
          throw new IOException("An error occured while downloading the specified URL (%s)." format arg.url, e)
        case Right(body) =>
          body
      }

    new SeResDownload(body)
  }

  private def getCookies =
    d.manage.getCookies.map { c =>
      new Cookie(c.getDomain, c.getName, c.getValue, c.getPath, expiryToInt(c.getExpiry), c.isSecure)
  }

  private def expiryToInt(expiry: ju.Date) =
    if (expiry == null) {
      -1
    } else {
      (expiry.getTime / 1000).toInt
    }
}