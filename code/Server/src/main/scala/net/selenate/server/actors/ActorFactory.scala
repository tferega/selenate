package net.selenate
package server
package actors

import akka.actor.{ Actor, ActorRef, ActorSystem, Address, Props, TypedActor, TypedProps }

import scala.reflect.ClassTag

object ActorFactory {
  private val log = Log(ActorFactory.getClass)

  log.info("Firing up main Actor System.")
  val address = Address("akka", "main", "selenate-server", 9072)
  val system = ActorSystem("main")
  private implicit val overlord = system.actorOf(Props[Overlord], name = "overlord")
  private def getTypedClass[T](i: T) = i.getClass.asInstanceOf[Class[T]]


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
