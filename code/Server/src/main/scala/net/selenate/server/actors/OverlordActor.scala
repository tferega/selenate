package net.selenate
package server
package actors

import akka.actor.{ Actor, ActorRef, Props }
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

object Overlord {
  private implicit val timeout = Timeout(10 second)
  private val log = Log(classOf[Overlord])

  def apply(props: Props, name: String)(implicit overlord: ActorRef): ActorRef = {
    val f  = overlord ? ((props, name))
    val tf = f.mapTo[ActorRef]

    val start = System.currentTimeMillis()

    val res = Await.result(tf, 10 second)

    tf onComplete {
      case _ =>
        val end = System.currentTimeMillis()
        log.debug("###===---> OVERLORD: APPLY FOR NAME(%s) TOOK: %s milis." format (name, (end-start).toString))
      }

    res
  }
}

class Overlord extends Actor {
  import Overlord._

  private val log = Log(classOf[Overlord])

  def receive = {
    case (props: Props, name: String) =>
      log.info("Actor request: %s" format name)

      val start = System.currentTimeMillis()
      log.debug("###===---> OVERLORD: GETTING CHILDREN FOR NAME(%s) START!" format name)

      val children = context.children

      log.debug("###===---> OVERLORD: AFTER CHILDREN FOR NAME(%s) TOOK: %s milis." format (name, (System.currentTimeMillis()-start).toString))

      val actor = context.children.find(_.path.name == name) match {
        case Some(child) =>
          log.debug("Actor %s already exists. Returning." format name)
          child
        case None =>
          log.debug("Actor %s does not exist. Creating." format name)
          context.actorOf(props, name)
      }

      log.debug("###===---> OVERLORD: AFTER FIND FOR NAME(%s) TOOK: %s milis." format (name, (System.currentTimeMillis()-start).toString))

      sender ! actor
  }
}
