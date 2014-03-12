package net.selenate.server
package driver

import info.PoolInfo
import selenium.SelenateFirefox

import java.util.UUID
import scala.collection.mutable.Queue
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._

private[driver] class DriverPoolActor(val info: PoolInfo) extends IDriverPoolActor {
  private case class DriverEntry(uuid: UUID, future: Future[SelenateFirefox])

  private val log = Log(classOf[DriverPoolActor])
  private val pool = new Queue[DriverEntry]
  val profile = info.profile

  log.info("Firing up Firefox Driver Pool Actor. Initial size: %d." format info.size)
  enqueueNew(info.size)

  private def enqueueNew(count: Int) {
    for (i <- 1 to count) {
      enqueueNew
    }
  }

  private def enqueueNew() {
    val uuid = UUID.randomUUID

    val driverFuture = Future {
      log.info("Driver pool actor starting a new entry: {%s}." format uuid)
      val driver = FirefoxRunner.run(profile)
      log.info("Driver pool actor entry {%s} started." format uuid)
      driver
    }
    val driverEntry = DriverEntry(uuid, driverFuture)
    pool.enqueue(driverEntry)
  }

  def get: SelenateFirefox = {
    enqueueNew
    val driverEntry = pool.dequeue
    log.info("Driver pool actor waiting for entry {%s}" format driverEntry.uuid)
    val driver = Await.result(driverEntry.future, 30 seconds)
    log.info("Driver pool actor returning entry {%s}" format driverEntry.uuid)
    driver
  }
}
