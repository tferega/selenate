package net.selenate.client.user;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import scala.concurrent.Await;
import scala.concurrent.Future;
import net.selenate.common.sessions.ISessionFactory;
import net.selenate.common.user.Preferences;
import akka.actor.*;
import scala.concurrent.duration.Duration;
import static java.util.concurrent.TimeUnit.SECONDS;

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

  public static ActorRef getSession(String name, int timeout) throws IOException {
    try {
      final Future<ActorRef> sessionFuture = sessionFactory.getSession(name);
      final ActorRef result = Await.result(sessionFuture, Duration.create(timeout, SECONDS));
      return result;
    } catch (final Exception e) {
      throw new IOException(String.format("An error occured while trying to get a new session (%s)!", name), e);
    }
  }

  public static ActorRef getSession(String name, Preferences preferences, int timeout) throws IOException {
    try {
      final Future<ActorRef> sessionFuture = sessionFactory.getSession(name, preferences);
      final ActorRef result = Await.result(sessionFuture, Duration.create(timeout, SECONDS));
      return result;
    } catch (final Exception e) {
      throw new IOException(String.format("An error occured while trying to get a new session (%s)!", name), e);
    }
  }

  public static void shutdown() {
    system.shutdown();
  }
}
