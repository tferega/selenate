package net.selenate.common.actors;

import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.UntypedActor;

public class ActorBuilder {
  private final String name;
  private final Class<? extends UntypedActor> clazz;
  private final ActorRefFactory actorContext;
  private final String dispatcher;
  private final Object[] args;

  private ActorBuilder(
      final String name,
      final Class<? extends UntypedActor> clazz,
      final ActorRefFactory actorContext,
      final String dispatcher,
      final Object[] args) {
    this.name = name;
    this.clazz = clazz;
    this.actorContext = actorContext;
    this.dispatcher = dispatcher;
    this.args = args;
  }

  public ActorBuilder(
      final String name,
      final Class<? extends UntypedActor> clazz) {
    this.name = name;
    this.clazz = clazz;
    this.actorContext = null;
    this.dispatcher = null;
    this.args = null;
  }

  public ActorRef create() {
    return ActorFactory.getInstance().createActor(name, clazz, actorContext, dispatcher, args);
  }

  public ActorBuilder withActorContext(final ActorRefFactory newActorContext) {
    return new ActorBuilder(name, clazz, newActorContext, dispatcher, args);
  }

  public ActorBuilder withDispatcher(final String newDispatcher) {
    return new ActorBuilder(name, clazz, actorContext, newDispatcher, args);
  }

  public ActorBuilder withArgs(final Object... newArgs) {
    return new ActorBuilder(name, clazz, actorContext, dispatcher, newArgs);
  }
}
