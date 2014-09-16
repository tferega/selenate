package net.selenate.server
package actions

import net.selenate.common.comms.req.SeCommsReq
import org.openqa.selenium.remote.RemoteWebElement
import scala.concurrent.duration._

object SessionContext {
  def default = SessionContext(true, 30L.seconds, IndexedSeq.empty, MapCache.empty)
}

case class SessionContext(
    var useFrames: Boolean,
    var keepaliveDelay: FiniteDuration,
    var keepaliveReqList: IndexedSeq[SeCommsReq],
    val elementCache: MapCache[String, RemoteWebElement]
)
