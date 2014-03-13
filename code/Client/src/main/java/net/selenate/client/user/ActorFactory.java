package net.selenate.client.user;

import static java.util.concurrent.TimeUnit.SECONDS;

import akka.actor.*;
import java.io.IOException;
import net.selenate.client.C;
import net.selenate.common.sessions.ISessionFactory;
import net.selenate.common.sessions.SessionOptions;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public final class ActorFactory {
  private ActorFactory() {}

  public static final ActorSystem system = ActorSystem.create("client-system");
  public static ISessionFactory sessionFactory = getTyped(ISessionFactory.class);

  public static <T> T getTyped(Class<T> clazz) {
    final String serverURI = String.format("akka://server-system@%s:%s/%s", C.ServerHost, C.ServerPort, C.ServerPath);
    return TypedActor.get(system).typedActorOf(
        new TypedProps<T>(clazz),
        system.actorFor(serverURI)
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

  public static ActorRef getSession(String name, SessionOptions options, int timeout) throws IOException {
    try {
      final Future<ActorRef> sessionFuture = sessionFactory.getSession(name, options);
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
