package net.selenate.server

import sessions.SessionFactoryActor

import net.selenate.server.driver.DriverPoolActor
import scala.io.StdIn.readLine

object EntryPoint extends App with Loggable {
  try {
    logInfo("Selenate Server now starting.")
    logInfo("Press ENTER to shut down.")

    logInfo("Loading configuration...")
    logInfo("Branch: " + C.branch)
    logInfo("ServerHost: " + C.Server.host)

    logInfo("Starting driver pool")
    actors.system.actorOf(DriverPoolActor.props(C.Server.Pool.poolInfo), "driver-pool")

    logInfo("Starting session factory")
    actors.system.actorOf(SessionFactoryActor.props, "session-factory")

    readLine
    logInfo("Selenate Server now shutting down.")
    actors.shutdown
    logInfo("HALTING")
    Runtime.getRuntime.halt(0)
  } catch {
    case e: Exception =>
      logError("An unexpected error occured!")
      logError(e.stackTrace)
      logError("HALTING!")
      Runtime.getRuntime.halt(-1)
  }
}
