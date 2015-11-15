package net.selenate.server
package actions

import net.selenate.common.comms.req.SeCommsReq
import net.selenate.common.comms.SeElementSelector
import org.openqa.selenium.remote.RemoteWebElement
import scala.concurrent.duration._

case class CachedElement(windowHandle: String, framePath: Vector[Int], elem: RemoteWebElement)

object SessionContext {
  def default = SessionContext(
      false,
      IndexedSeq.empty, IndexedSeq.empty,
      30L.seconds, IndexedSeq.empty,
      20000, 250, 0,
      MapCache.empty)
}

case class SessionContext(
    var useFrames: Boolean,
    var persistentPresentSelectorList: IndexedSeq[SeElementSelector],
    var persistentAbsentSelectorList: IndexedSeq[SeElementSelector],
    var keepaliveDelay: FiniteDuration,
    var keepaliveReqList: IndexedSeq[SeCommsReq],
    var waitTimeout: Long,
    var waitResolution: Long,
    var waitDelay: Long,
    val elementCache: MapCache[String, CachedElement]
)
