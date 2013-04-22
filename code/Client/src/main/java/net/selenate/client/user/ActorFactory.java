package net.selenate.client.user;

import net.selenate.common.sessions.ISessionFactory;
import net.selenate.common.user.Preferences;
import akka.actor.*;

public final class ActorFactory {
  private ActorFactory() {}

  public static final ActorSystem system = ActorSystem.create("selenium-client");
  public static ISessionFactory sessionFactory = getTyped(ISessionFactory.class);

  public static <T> T getTyped(Class<T> clazz) {
    return TypedActor.get(system).typedActorOf(
        new TypedProps<T>(clazz),
        system.actorFor("akka://main@selenate-server:9072/user/session-factory")
    );
  }

  public static ActorRef getUntyped(String name, Class<? extends Actor> clazz) {
    return system.actorOf(new Props(clazz), name);
  }

  public static ActorRef getSession(String name) {
    final String sessionPath = sessionFactory.getSession(name);
    return system.actorFor(sessionPath);
  }

  public static ActorRef getSession(String name, Preferences preferences) {
    final String sessionPath = sessionFactory.getSession(name, preferences);
    return system.actorFor(sessionPath);
  }

  public static void shutdown() {
    system.shutdown();
  }
}
