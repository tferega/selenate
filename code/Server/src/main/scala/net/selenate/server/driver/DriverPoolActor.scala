package net.selenate.server
package driver

import extensions.SelenateFirefox
import linux.LinuxDisplay
import settings.PoolSettings

import akka.actor.{ Actor, ActorRef, Props }
import net.selenate.common.sessions.SessionRequest
import net.selenate.common.NamedUUID
import scala.collection.mutable.Queue
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._

object DriverPoolActor {
  def props(settings: PoolSettings): Props = Props(new DriverPoolActor(settings))

  case class Dequeue(requester: ActorRef, sessionRequest: SessionRequest)

  private val uuidFactory = new NamedUUID("Driver")
  case class DriverEntry(uuid: String, future: Future[SelenateFirefox])
}

class DriverPoolActor(val settings: PoolSettings)
    extends Actor
    with Loggable {
  import DriverPoolActor._

  private val pool = new Queue[DriverEntry]
  val profile = settings.profile

  logDebug(s"""Driver pool with size ${ settings.size } started""")
  for (i <- 1 to settings.size) {
    enqueue()
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
    case Dequeue(requester, sessionRequest) =>
      logDebug(s"""Received Dequeue($sessionRequest)""")
      enqueue()
      sender ! ((requester, sessionRequest, dequeue()))
  }

  override def postStop() {
    val futureList: List[Future[SelenateFirefox]] = pool.toList.map(_.future)
    val listFuture: Future[List[SelenateFirefox]] = Future.sequence(futureList)
    val driverList = Await.result(listFuture, C.Server.Timeouts.SHUTDOWN.duration)
    driverList foreach { driver =>
      driver.quit
      driver.displayInfo foreach LinuxDisplay.destroy
    }
    logDebug("Driver pool stopped")
  }
}
