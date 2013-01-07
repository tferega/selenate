package net.selenate
package server
package sessions

import common.comms.req.SeCommsReq
import akka.util.Duration
import net.selenate.common.comms.req.SeReqKeepalive
import akka.util.duration._
import scala.collection.JavaConversions._

object KeepaliveMsg {
  def fromReq(req: SeReqKeepalive) = {
    val delay = req.delayMillis.milliseconds
    val reqList = req.reqList.toIndexedSeq

    KeepaliveMsg(delay, reqList)
  }
}
case class KeepaliveMsg(delay: Duration, reqList: IndexedSeq[SeCommsReq])
