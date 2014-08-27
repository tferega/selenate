package net.selenate.server

import driver.DriverPoolActor
import sessions.SessionFactoryActor
import settings.PoolSettings

import scala.io.StdIn.readLine

object EntryPoint extends App with Loggable {
  try {
    logInfo("Selenate Server now starting...")
    logInfo("Press ENTER to shut down.")
    startup()

    readLine // ==========-----> MAIN RUNTIME <-----==========

    logInfo("Selenate Server now shutting down...")
    shutdown()
  } catch {
    case e: Exception =>
      logError("An unexpected error occured!", e)
      logError("HALTING!")
      Runtime.getRuntime.halt(1)
  }

  private def startup() {
    logInfo("Loading configuration...")
    logInfo("Branch: " + C.BRANCH)
    logInfo("ServerHost: " + C.Server.HOST)

    logInfo("Starting main Actor system...")
    val pool = C.Server.Pool
    val poolInfo = PoolSettings.fromConfig(pool.SIZE, pool.DISPLAY, pool.RECORD, pool.BINARY, pool.PREFS)
    actors.system.actorOf(DriverPoolActor.props(poolInfo), "driver-pool")
    actors.system.actorOf(SessionFactoryActor.props, "session-factory")
  }

  private def shutdown() {
    logInfo("Shutting down main actor system...")
    actors.shutdown

    logInfo("HALTING")
    Runtime.getRuntime.halt(0)
  }
}
