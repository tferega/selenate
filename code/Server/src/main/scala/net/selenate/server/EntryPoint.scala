package net.selenate.server

import sessions.SessionFactoryActor

import net.selenate.server.driver.DriverPoolActor
import scala.io.StdIn.readLine

object EntryPoint extends App with Loggable {
  try {
    logInfo("Selenate Server now starting...")
    logInfo("Press ENTER to shut down.")
    startup()

    readLine

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
    logInfo("Branch: " + C.branch)
    logInfo("ServerHost: " + C.Server.host)

    logInfo("Starting main Actor system...")
    actors.system.actorOf(DriverPoolActor.props(C.Server.Pool.poolInfo), "driver-pool")
    actors.system.actorOf(SessionFactoryActor.props, "session-factory")
  }

  private def shutdown() {
    logInfo("Shutting down main actor system...")
    actors.shutdown

    logInfo("HALTING")
    Runtime.getRuntime.halt(0)
  }
}
