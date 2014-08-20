package net.selenate.server

import akka.actor.ActorSystem

package object actors {
  private val log = Log(this.getClass)

  log.info("Starting Akka")
  val system = ActorSystem("server-system", C.Akka.config)

  def shutdown() {
    log.info("Shutting down main Actor System")
    system.shutdown
  }
}
