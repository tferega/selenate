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
import net.selenate.common.comms.SeDownloadMethod
import com.ning.http.client.ProxyServer

class DownloadAction(val d: FirefoxDriver) extends IAction[SeReqDownload, SeResDownload] {

  protected val log = Log(classOf[DownloadAction])

  def act = { arg =>
    val r1 = url(arg.url)

    import SeDownloadMethod._
    val r2 =
      arg.method match {
        case GET  => r1.GET
        case POST => r1.POST
      }

    val r3 =
      Option(arg.body) match {
        case Some(body) => r2.setBody(body)
        case None       => r2
      }

//    r3.setProxyServer(new ProxyServer("localhost", 8888))
    val request = r3

    arg.headers.foreach{
      case (k,v) =>
        log.debug("  Key: " + k + " value: " + v)
        request.setHeader(k, v)
    }

    getCookies foreach request.addCookie

    val body =
      Http(request.secure OK as.Bytes).either.apply match {
        case Left(e) =>
          throw new IOException("An error occured while downloading the specified request (%s)." format arg, e)
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
