package net.selenate.client.user;

import net.selenate.common.sessions.ISessionFactory;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.TypedProps;
import akka.actor.ActorSystem;
import akka.actor.TypedActor;

public final class ActorFactory {
  private ActorFactory() {}

  public static final ActorSystem system = ActorSystem.create("selenium-client");
  public static ISessionFactory sessionFactory = getTyped(ISessionFactory.class);

  public static <T> T getTyped(Class<T> clazz) {
    return TypedActor.get(system).typedActorOf(
        new TypedProps<T>(clazz),
        system.actorFor("akka://main@selenate-server:9070/user/session-factory")
    );
  }

  public static ActorRef getUntyped(String name, Class<? extends Actor> clazz) {
    return system.actorOf(new Props(clazz), name);
  }

  public static ActorRef getSession(String name) {
    final String sessionPath = sessionFactory.getSession(name);
    return system.actorFor(sessionPath);
  }

  public static void shutdown() {
    system.shutdown();
  }
}
