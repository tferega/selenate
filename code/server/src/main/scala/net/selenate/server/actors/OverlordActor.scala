package net.selenate.server
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

  def receive = {
    case (props: Props, name: String) =>
      println("GOT A REQUEST FOR ACTOR: "+ name)
      val actor = context.children.find(_.path.name == name) match {
        case Some(child) =>
          println("ACTOR ALREADY EXISTS; RETURNING")
          child
        case None =>
          println("ACTOR DOES NOT EXIST; CREATING")
          context.actorOf(props, name)
      }
      sender ! actor
  }
}
