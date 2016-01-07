package net.selenate.server

import driver.DriverPoolActor
import sessions.SessionFactoryActor
import settings.PoolSettings

object EntryPoint extends App with Loggable {
  try {
    logInfo("Selenate Server now starting...")

    logInfo("Registering shutdown hook...");
    sys.addShutdownHook(shutdownHook)

    logInfo("Loading configuration...")
    logInfo("Branch: " + C.BRANCH)
    val poolInfo = PoolSettings.fromConfig(C.Server.Pool.SIZE, C.Server.Pool.DISPLAY, C.Server.Pool.BINARY, C.Server.Pool.PREFS)

    logInfo("Starting DriverPool actor...")
    actors.system.actorOf(DriverPoolActor.props(poolInfo), "driver-pool")

    logInfo("Starting SessionFactory actor...")
    actors.system.actorOf(SessionFactoryActor.props, "session-factory")

    logInfo("Selenate now running");
  } catch {
    case e: Exception =>
      logError("An unexpected error occured!", e)
      sys.exit(-1);
  }

  private def shutdownHook() {
    logInfo("Selenate Server now shutting down...")
    logInfo("Shutting down main actor system...")
    actors.shutdown

    logInfo("HALTING")
    Runtime.getRuntime.halt(0)
  }
}
