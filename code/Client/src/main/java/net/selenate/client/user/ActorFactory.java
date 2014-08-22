package net.selenate.client.user;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import java.io.IOException;
import java.util.Optional;
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
      sessionFactory = waitForActor("session-factory");
    } catch (final Exception e) {
      throw new RuntimeException("An error occured while waiting for session factory actor!", e);
    }
  }

  private static Optional<ActorRef> tryFindSelection(final long timeout, final ActorSelection selection) {
    try {
      final FiniteDuration duration = Utils.getDuration(timeout);
      final Future<ActorRef> actorFuture = selection.resolveOne(duration);
      final ActorRef result = Await.result(actorFuture, duration);
      return Optional.of(result);
    } catch (final Exception e) {
      return Optional.empty();
    }
  }
  public static ActorRef waitForActor(String path) throws IOException {
    final String uri = String.format("akka.tcp://server-system@%s:%s/user/%s", C.ServerHost, C.ServerPort, path);
    final ActorSelection selection = system.actorSelection(uri);
    final Optional<ActorRef> result = Utils.waitForEvent(
        (final long t) -> tryFindSelection(t, selection),
        150l,
        C.WaitingTimeout * 1000l
    );

    if (result.isPresent()) {
      return result.get();
    } else {
      throw new IOException(String.format("Could not resolve actor (%s) in %d seconds!", uri, C.WaitingTimeout));
    }
  }

  public static ActorRef waitForSession(String sessionID) throws IOException {
    try {
      sessionFactory.tell(new SessionRequest(sessionID), null);
      final ActorRef session = waitForActor("session-factory/" + sessionID);
      return session;
    } catch (final Exception e) {
      throw new IOException(String.format("An error occured while trying to get a new session (%s)!", sessionID), e);
    }
  }

  public static void shutdown() {
    system.shutdown();
  }
}
