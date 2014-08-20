package net.selenate.server
package driver

import extensions.SelenateFirefox
import info.PoolInfo

import akka.actor.{ Actor, Props }
import scala.collection.mutable.Queue
import scala.concurrent.Future

object DriverPoolActor {
  def props(info: PoolInfo): Props = Props(new DriverPoolActor(info))

  case class Dequeue(sessionID: String)
  private case object Enqueue

  private val uuidFactory = new NamedUUID("Driver")
  case class DriverEntry(uuid: String, future: Future[SelenateFirefox])
}

class DriverPoolActor(val info: PoolInfo) extends Actor {
  import DriverPoolActor._

  private val log = Log(this.getClass)
  private val pool = new Queue[DriverEntry]
  val profile = info.profile

  log.debug(s"""Driver pool with size ${ info.size } created""")
  for (i <- 1 to info.size) {
    self ! Enqueue
  }

  private def dequeue(): DriverEntry = {
    val driverEntry = pool.dequeue
    log.debug(s"""Driver pool returning an entry: ${ driverEntry.uuid }""")
    driverEntry
  }

  private def enqueue() {
    val uuid = uuidFactory.random

    val driverFuture = Future {
      log.debug(s"""Driver pool starting a new entry: $uuid""")
      val driver = FirefoxRunner.run(profile)
      log.debug(s"""Driver pool entry $uuid started""")
      driver
    }
    val driverEntry = DriverEntry(uuid, driverFuture)
    pool.enqueue(driverEntry)
  }

  def receive = {
    case Dequeue(sessionID) =>
      log.debug(s"""Received Dequeue($sessionID)""")
      self ! Enqueue
      sender ! ((sessionID, dequeue()))

    case Enqueue =>
      log.debug("Received Enqueue")
      enqueue()
  }
}
