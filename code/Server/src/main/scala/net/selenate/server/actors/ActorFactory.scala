package net.selenate
package server
package actors

import akka.actor.{ Actor, ActorRef, ActorSystem, Address, Props, TypedActor, TypedProps }
import scala.reflect.ClassTag
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigValueFactory

object ActorFactory {
  private val log = Log(ActorFactory.getClass)

  log.info("Firing up main Actor System.")
  val akkaConfig = getConfig()
  val system = ActorSystem("server-system", akkaConfig)
  private implicit val overlord = system.actorOf(Props[Overlord], name = "overlord")
  private def getTypedClass[T](i: T) = i.getClass.asInstanceOf[Class[T]]


  def getConfig() = {
    val config = ConfigFactory.load
    val hostname = ConfigValueFactory.fromAnyRef(C.Server.host)
    config.withValue("akka.remote.netty.hostname", hostname)
  }

  def shutdown() {
    log.info("Shutting down main Actor System.")
    system.shutdown
  }

  def typed[I <: AnyRef](name: String, instance: I): I = {
    log.debug("Main System creating a new typed actor: %s [%s].".format(name, instance.getClass.toString));
    val props = TypedProps(getTypedClass(instance), instance)
    TypedActor(system).typedActorOf(props, name)
  }

  def untyped[T <: Actor](name: String)(implicit m: ClassTag[T]): ActorRef = {
    log.debug("Main System creating a new untyped actor: %s." format name)
    Overlord(Props[T], name)
  }

  def untyped[T <: Actor](name: String, instanceFactory: () => T): ActorRef = {
    log.debug("Main System creating a new untyped actor: %s." format name)
    Overlord(Props(instanceFactory()), name)
  }
}
