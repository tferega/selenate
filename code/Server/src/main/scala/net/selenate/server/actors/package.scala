package net.selenate.server

import akka.actor.ActorSystem
import scala.concurrent.Await
import scala.concurrent.duration.Duration

package object actors extends Loggable {
  val system = ActorSystem("server-system", C.Akka.CONFIG)

  def shutdown() {
    system.terminate
    Await.result(system.whenTerminated, Duration.Inf)
  }
}
