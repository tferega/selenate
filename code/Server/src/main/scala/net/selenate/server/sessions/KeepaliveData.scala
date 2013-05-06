package net.selenate
package server
package sessions

import common.comms.req.SeCommsReq
import common.comms.req.SeReqStartKeepalive

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
