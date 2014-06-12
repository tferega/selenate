package net.selenate.client.user;

import java.io.IOException;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;

import scala.concurrent.Await;
import scala.concurrent.Future;
import net.selenate.client.C;
import net.selenate.common.sessions.ISessionFactory;
import net.selenate.common.user.Preferences;
import akka.actor.*;
import scala.concurrent.duration.Duration;
import static java.util.concurrent.TimeUnit.SECONDS;

public final class ActorFactory {
  private ActorFactory() {}

  private static final Config config     = loadConfig();
  public static final ActorSystem system = ActorSystem.create("client-system", config);
  public static ISessionFactory sessionFactory = getTyped(ISessionFactory.class);

  public static <T> T getTyped(Class<T> clazz) {
    final String serverURI = String.format("akka://server-system@%s:%s/%s", C.ServerHost, C.ServerPort, C.ServerPath);
    return TypedActor.get(system).typedActorOf(
        new TypedProps<T>(clazz),
        system.actorSelection(serverURI).anchor()
    );
  }

  public static Config loadConfig() {
    Config config        = ConfigFactory.load();
    ConfigValue hostname = ConfigValueFactory.fromAnyRef(C.ClientHost);
    return config.withValue("akka.remote.netty.hostname", hostname);
  }

  public static ActorRef getUntyped(String name, Class<? extends Actor> clazz) {
    return system.actorOf(Props.create(clazz), name);
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
