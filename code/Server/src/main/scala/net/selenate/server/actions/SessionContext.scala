package net.selenate.server
package actions

import net.selenate.common.comms.req.SeCommsReq
import org.openqa.selenium.remote.RemoteWebElement
import scala.concurrent.duration._

case class CachedElement(windowHandle: String, framePath: Vector[Int], elem: RemoteWebElement)

object SessionContext {
  def default = SessionContext(false, 30L.seconds, IndexedSeq.empty, MapCache.empty)
}

case class SessionContext(
    var useFrames: Boolean,
    var keepaliveDelay: FiniteDuration,
    var keepaliveReqList: IndexedSeq[SeCommsReq],
    val elementCache: MapCache[String, CachedElement]
)
