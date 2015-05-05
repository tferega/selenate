package net.selenate
package server

import actors.ActorFactory
import common.sessions.ISessionFactory
import driver.DriverPool
import sessions._
import scala.concurrent.duration._
import scala.annotation.tailrec

object EntryPoint extends App {
  private val log = Log(EntryPoint.getClass)

  try {
    log.info("Selenate Server now starting.")
    log.info("Press ENTER to shut down.")

    log.info("Loading Configs for server...")
    log.info("  Branch     : " + C.branch)
    log.info("  ServerHost : " + C.Server.host)
    log.info("  PoolSize   : " + C.Server.poolSize.toString)

    SessionFactory
    DriverPool
    HouseKeeping.schedule(1 hour)

    readLine
    while(!ActorFactory.system.isTerminated) {
      Thread.sleep(10000)
    }
    log.info("Selenate Server now shutting down.")
    ActorFactory.shutdown()
    Runtime.getRuntime.halt(0)
  } catch {
    case e: Exception =>
      log.error("An unexpected error occured!")
      log.error(e.stackTrace)
      log.error("HALTING!")
      Runtime.getRuntime.halt(-1)
  }
}
