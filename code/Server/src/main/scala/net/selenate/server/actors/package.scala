package net.selenate.server

import akka.actor.ActorSystem

package object actors extends Loggable {
  logInfo("Starting Akka")
  val system = ActorSystem("server-system", C.Akka.config)

  def shutdown() {
    logInfo("Shutting down main Actor System")
    system.shutdown
  }
}
