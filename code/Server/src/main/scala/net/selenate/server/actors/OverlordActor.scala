package net.selenate
package server
package actors

import akka.actor.{ Actor, ActorRef, Props }
import akka.dispatch.Await
import akka.pattern.ask
import akka.util.duration._
import akka.util.Timeout

object Overlord {
  private implicit val timeout = Timeout(1 second)

  def apply(props: Props, name: String)(implicit overlord: ActorRef): ActorRef = {
    val f  = overlord ? (props, name)
    val tf = f.mapTo[ActorRef]
    Await.result(tf, 1 second)
  }
}

class Overlord extends Actor {
  import Overlord._

  private val log = Log(classOf[Overlord])

  def receive = {
    case (props: Props, name: String) =>
      log.info("Actor request: %s" format name)
      val actor = context.children.find(_.path.name == name) match {
        case Some(child) =>
          log.debug("Actor %s already exists. Returning." format name)
          child
        case None =>
          log.debug("Actor %s does not exist. Creating." format name)
          context.actorOf(props, name)
      }
      sender ! actor
  }
}
