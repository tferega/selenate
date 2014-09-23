package net.selenate.server
package actions
package workers

import extensions.SelenateFirefox

import com.ning.http.client.cookie.{ Cookie => HttpClientCookie}
import dispatch._
import java.io.IOException
import net.selenate.common.comms.req.SeReqSessionDownload
import net.selenate.common.comms.res.SeResSessionDownload
import net.selenate.common.exceptions.SeActionException
import scala.collection.JavaConversions._

class SessionDownloadAction(val sessionID: String, val context: SessionContext, val d: SelenateFirefox)
    extends Action[SeReqSessionDownload, SeResSessionDownload]
    with ActionCommons {
  def doAct = { arg =>
    val request = url(arg.getUrl)
    request.setHeader("Referer", d.getCurrentUrl)
    request.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:12.0) Gecko/20100101 Firefox/12.0")
    getCookies foreach request.addCookie

    val body =
      Http(request OK as.Bytes).either.apply match {
        case Left(e) =>
          throw new SeActionException(name, arg, s"could not download specified URL ${ arg.getUrl }", e)
        case Right(body) =>
          body
      }

    new SeResSessionDownload(body)
  }

  private def getCookies =
    d.manage.getCookies.map { c =>
      val rawValue = c.getValue
      val maxAge = -1
      val isHttpOnly = false
      HttpClientCookie.newValidCookie(c.getName, c.getValue, c.getDomain, rawValue, c.getPath, expiryToLong(c.getExpiry), maxAge, c.isSecure, isHttpOnly)
  }

  private def expiryToLong(expiry: java.util.Date) =
    if (expiry == null) {
      -1L
    } else {
      expiry.getTime / 1000L
    }
}
