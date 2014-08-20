package net.selenate.client.user;

import static java.util.concurrent.TimeUnit.SECONDS;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;
import java.io.IOException;
import net.selenate.client.C;
import net.selenate.common.sessions.SessionRequest;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

public final class ActorFactory {
  private ActorFactory() {}

  private static final Config config     = loadConfig();
  public static final ActorSystem system = ActorSystem.create("client-system", config);
  public static final ActorRef sessionFactory; static {
    try {
      sessionFactory = waitForActor("akka.tcp://server-system@10.5.100.199:9072/user/session-factory", 30);
    } catch (final Exception e) {
      throw new RuntimeException("An error occured while waiting for session factory actor!", e);
    }
  }

  private static Config loadConfig() {
    Config config        = ConfigFactory.load();
    ConfigValue hostname = ConfigValueFactory.fromAnyRef(C.ClientHost);
    final Config r = config.withValue("akka.remote.netty.hostname", hostname);
    System.out.println(r);
    return r;
  }

  private static FiniteDuration getDuration(int timeout) {
    return FiniteDuration.create(timeout, SECONDS);
  }

  public static ActorRef waitForActor(String uri, int timeout) throws IOException {
    try {
      final FiniteDuration duration = getDuration(timeout);
      final ActorSelection selection = system.actorSelection(uri);
      final Future<ActorRef> actorFuture = selection.resolveOne(duration);
      return Await.result(actorFuture, duration);
    } catch (final Exception e) {
      throw new IOException(String.format("An error occured while waiting for an actor (%s)!", uri), e);
    }
  }

  public static ActorRef waitForSession(String sessionID, int timeout) throws IOException {
    try {
      sessionFactory.tell(new SessionRequest(sessionID), null);
      final ActorRef session = waitForActor("akka.tcp://server-system@10.5.100.199:9072/user/session-factory/" + sessionID, timeout);
      return session;
    } catch (final Exception e) {
      throw new IOException(String.format("An error occured while trying to get a new session (%s)!", sessionID), e);
    }
  }

  public static void shutdown() {
    system.shutdown();
  }
}
