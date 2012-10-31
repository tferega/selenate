package net.selenate.server

import actions._
import comms._

import akka.actor.{ Actor, ActorSystem, ActorRef, Props, TypedActor, TypedProps }

object ActorFactory {
  val Prefix = "user/"
  private val system = ActorSystem("selenate-server")

  private def getTypedClass[T](i: T) = i.getClass.asInstanceOf[Class[T]]

  def shutdown() {
    system.shutdown
  }

  def typed[T <: AnyRef](name: String, instance: T): ActorRef = {
    TypedActor(system).typedActorOf(
      TypedProps(
        getTypedClass(instance),
        instance
      ).copy(timeout = Some(30))
      , name
    )
    system.actorFor(Prefix + name)
  }

  def untyped[T <: Actor](name: String)(implicit arg0: ClassManifest[T]): ActorRef =
    system.actorOf(Props[T], name = name)

  def untyped[T <: Actor](name: String, instanceFactory: () => T): ActorRef =
    system.actorOf(Props(instanceFactory()), name = name)
}
