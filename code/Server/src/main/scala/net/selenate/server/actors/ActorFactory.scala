package net.selenate
package server
package actors

import akka.actor.{ Actor, ActorRef, ActorSystem, Address, Props, TypedActor, TypedProps }

object ActorFactory {
  val address = Address("akka", "main", "selenate-server", 9070)
  val system = ActorSystem("main")
  private implicit val overlord = system.actorOf(Props[Overlord], name = "overlord")
  private def getTypedClass[T](i: T) = i.getClass.asInstanceOf[Class[T]]


  def shutdown() {
    system.shutdown
  }

  def typed[I <: AnyRef](name: String, instance: I): I = {
    val props = TypedProps(getTypedClass(instance), instance)
    TypedActor(system).typedActorOf(props, name)
  }

  def untyped[T <: Actor](name: String)(implicit m: ClassManifest[T]): ActorRef =
    Overlord(Props[T], name)

  def untyped[T <: Actor](name: String, instanceFactory: () => T): ActorRef =
    Overlord(Props(instanceFactory()), name)
}
