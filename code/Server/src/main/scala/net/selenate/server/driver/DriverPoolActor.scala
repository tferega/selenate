package net.selenate
package server
package driver

import DriverPoolActor.GetDriver
import akka.actor.actorRef2Scala
import akka.actor.Actor
import akka.dispatch.Await
import akka.dispatch.Future
import akka.pattern.ask
import akka.util.duration.intToDurationInt
import akka.util.Timeout
import net.selenate.server.actors.ActorFactory
import org.openqa.selenium.firefox.FirefoxDriver
import scala.collection.mutable.Queue

class DriverPool(capacity: Int) {
  private val actor = new DriverPoolActor(capacity)
  def get = actor.get
}


object DriverPoolActor {
  private implicit val timeout = Timeout(1 second)

  private case object GetDriver
}

class DriverPoolActor(capacity: Int) extends Actor {
  import DriverPoolActor._

  implicit val system = ActorFactory.system
  private val log = Log(classOf[DriverPoolActor])
  private val pool = new Queue[Future[FirefoxDriver]]

  def get(): FirefoxDriver = {
    val rawFutureFuture = (self ? GetDriver).mapTo[Future[FirefoxDriver]]
    val driverFuture = rawFutureFuture.flatMap(identity)
    Await.result(driverFuture, 30 seconds)
  }

  private def enqueueNew {
    val driverFuture = Future {
      new FirefoxDriver
    }
    pool.enqueue(driverFuture)
  }

  def receive = {
    case GetDriver =>
      enqueueNew
      val driverFuture = pool.dequeue
      sender ! driverFuture
  }
}
