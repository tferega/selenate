package net.selenate.server

import driver.DriverPoolActor
import sessions.SessionFactoryActor
import settings.PoolSettings

import scala.io.StdIn.readLine

object Runner extends Loggable {
  def start() {
    try {
      logInfo("Selenate Server now starting...")
      if (C.Server.IS_KILLABLE) logInfo("Press ENTER to shut down.")

      logInfo("Loading configuration...")
      logInfo("Branch: " + C.BRANCH)
      logInfo("ServerHost: " + C.Server.HOST)

      logInfo("Starting main Actor system...")

      val pool = C.Server.Pool
      val poolInfo = PoolSettings.fromConfig(pool.SIZE, pool.DISPLAY, pool.BINARY, pool.PREFS)
      actors.system.actorOf(DriverPoolActor.props(poolInfo), "driver-pool")
      actors.system.actorOf(SessionFactoryActor.props, "session-factory")

      if (C.Server.IS_KILLABLE) {
        /* #########==========-----> MAIN RUNTIME <-----==========############# */
        /*                           \          /                               */
        /*                            \        /                                */
                                       readLine
        /*                            /        \                                */
        /*                           /          \                               */
        /* #########==========-----> MAIN RUNTIME <-----==========############# */

        logInfo("Selenate Server now shutting down...")
        logInfo("Shutting down main actor system...")
        actors.shutdown

        logInfo("HALTING")
        Runtime.getRuntime.halt(0)
      }
    } catch {
      case e: Exception =>
        logError("An unexpected error occured!", e)
        logError("HALTING!")
        Runtime.getRuntime.halt(1)
    }
  }
}
