package net.selenate
package server
package driver

import actors.ActorFactory

import akka.dispatch.{ Await, Future }
import akka.util.duration._

import java.util.UUID

import org.openqa.selenium.firefox.FirefoxDriver

import scala.collection.mutable.Queue

object DriverPool extends IDriverPool {
  val size = C.Server.poolSize
  val pool = ActorFactory.typed[IDriverPool]("driver-pool", new DriverPool(size))

  def get = pool.get
}

private class DriverPool(val size: Int) extends IDriverPool {
  private case class DriverEntry(uuid: UUID, future: Future[FirefoxDriver])

  private val log = Log(classOf[DriverPool])
  private val pool = new Queue[DriverEntry]

  log.info("Firing up Firefox Driver Pool. Initial size: %d." format size)
  enqueueNew(size)

  private def enqueueNew(count: Int) {
    for (i <- 1 to count) {
      enqueueNew
    }
  }

  private def enqueueNew {
    implicit val system = ActorFactory.system
    val uuid = UUID.randomUUID

    val driverFuture = Future {
      log.info("Driver pool starting a new entry: {%s}." format uuid)
      val driver = new FirefoxDriver
      log.info("Driver pool entry {%s} started." format uuid)
      driver
    }
    val driverEntry = DriverEntry(uuid, driverFuture)
    pool.enqueue(driverEntry)
  }

  def get: FirefoxDriver = {
    enqueueNew
    val driverEntry = pool.dequeue
    log.info("Driver pool waiting for entry {%s}" format driverEntry.uuid)
    val driver = Await.result(driverEntry.future, 30 seconds)
    log.info("Driver pool returning entry {%s}" format driverEntry.uuid)
    driver
  }
}
