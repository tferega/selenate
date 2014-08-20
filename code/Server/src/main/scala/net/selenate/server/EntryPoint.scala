package net.selenate.server

import sessions.SessionFactoryActor

import net.selenate.server.driver.DriverPoolActor
import scala.io.StdIn.readLine

object EntryPoint extends App {
  private val log = Log(this.getClass)

  try {
    log.info("Selenate Server now starting.")
    log.info("Press ENTER to shut down.")

    log.info("Loading configuration...")
    log.info("Branch: " + C.branch)
    log.info("ServerHost: " + C.Server.host)

    log.info("Starting driver pool")
    actors.system.actorOf(DriverPoolActor.props(C.Server.Pool.poolInfo), "driver-pool")

    log.info("Starting session factory")
    actors.system.actorOf(SessionFactoryActor.props, "session-factory")

    readLine
    log.info("Selenate Server now shutting down.")
    actors.shutdown
    log.info("HALTING")
    Runtime.getRuntime.halt(0)
  } catch {
    case e: Exception =>
      log.error("An unexpected error occured!")
      log.error(e.stackTrace)
      log.error("HALTING!")
      Runtime.getRuntime.halt(-1)
  }
}
