package net.selenate.client.user;

import static java.util.concurrent.TimeUnit.SECONDS;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import java.io.IOException;
import net.selenate.client.C;
import net.selenate.common.sessions.SessionRequest;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

public final class ActorFactory {
  private ActorFactory() {}

  public static final ActorSystem system = ActorSystem.create("client-system", C.AkkaConfig);
  public static final ActorRef sessionFactory; static {
    try {
      sessionFactory = waitForActor("session-factory", 30);
    } catch (final Exception e) {
      throw new RuntimeException("An error occured while waiting for session factory actor!", e);
    }
  }

  private static FiniteDuration getDuration(int timeout) {
    return FiniteDuration.create(timeout, SECONDS);
  }

  public static ActorRef waitForActor(String path, int timeout) throws IOException {
    final String uri = String.format("akka.tcp://server-system@%s:%s/user/%s", C.ServerHost, C.ServerPort, path);
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
      Thread.sleep(1000);
      final ActorRef session = waitForActor("session-factory/" + sessionID, timeout);
      return session;
    } catch (final Exception e) {
      throw new IOException(String.format("An error occured while trying to get a new session (%s)!", sessionID), e);
    }
  }

  public static void shutdown() {
    system.shutdown();
  }
}
