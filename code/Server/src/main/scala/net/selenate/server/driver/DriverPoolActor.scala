package net.selenate.server
package driver

import extensions.SelenateFirefox
import info.PoolInfo

import akka.actor.{ Actor, Props }
import net.selenate.common.util.NamedUUID
import scala.collection.mutable.Queue
import scala.concurrent.Future

object DriverPoolActor {
  def props(info: PoolInfo): Props = Props(new DriverPoolActor(info))

  case class Dequeue(sessionID: String)
  private case object Enqueue

  private val uuidFactory = new NamedUUID("Driver")
  case class DriverEntry(uuid: String, future: Future[SelenateFirefox])
}

class DriverPoolActor(val info: PoolInfo)
    extends Actor
    with Loggable {
  import DriverPoolActor._

  private val pool = new Queue[DriverEntry]
  val profile = info.profile

  logDebug(s"""Driver pool with size ${ info.size } created""")
  for (i <- 1 to info.size) {
    self ! Enqueue
  }

  private def dequeue(): DriverEntry = {
    val driverEntry = pool.dequeue
    logDebug(s"""Driver pool returning an entry: ${ driverEntry.uuid }""")
    driverEntry
  }

  private def enqueue() {
    val uuid = uuidFactory.random

    val driverFuture = Future {
      logDebug(s"""Driver pool starting a new entry: $uuid""")
      val driver = FirefoxRunner.run(profile)
      logDebug(s"""Driver pool entry $uuid started""")
      driver
    }
    val driverEntry = DriverEntry(uuid, driverFuture)
    pool.enqueue(driverEntry)
  }

  def receive = {
    case Dequeue(sessionID) =>
      logDebug(s"""Received Dequeue($sessionID)""")
      self ! Enqueue
      sender ! ((sessionID, dequeue()))

    case Enqueue =>
      logDebug("Received Enqueue")
      enqueue()
  }
}
