package net.selenate.server

import akka.actor.ActorSystem

package object actors extends Loggable {
  val system = ActorSystem("server-system", C.Akka.config)

  def shutdown() {
    system.shutdown
    system.awaitTermination
  }
}
