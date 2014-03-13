package net.selenate.server
package sessions

import net.selenate.common.comms.req.{ SeCommsReq, SeReqStartKeepalive }
import scala.concurrent.duration._
import scala.collection.JavaConversions._

object KeepaliveData {
  def fromReq(req: SeReqStartKeepalive) = {
    val delay = req.delayMillis.milliseconds
    val reqList = req.reqList.toIndexedSeq

    KeepaliveData(delay, reqList)
  }
}
case class KeepaliveData(delay: FiniteDuration, reqList: IndexedSeq[SeCommsReq])
