package net.selenate.client.user;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import java.io.IOException;
import net.selenate.client.C;
import net.selenate.common.sessions.SessionRequest;
import scala.concurrent.Await;
import scala.concurrent.Future;

public final class ActorFactory {
  private ActorFactory() {}

  public static final ActorSystem system = ActorSystem.create("client-system", C.config);
  public static final ActorRef sessionFactory; static {
    try {
      sessionFactory = waitForActor("session-factory");
    } catch (final Exception e) {
      throw new RuntimeException("An error occured while waiting for session factory actor!", e);
    }
  }

  private static ActorRef waitForActor(final String path) throws IOException {
    final String uri = String.format("akka.tcp://%s@%s:%s/user/%s", C.ServerSystem.Name, C.ServerSystem.Host, C.ServerSystem.Port, path);
    try {
      final ActorSelection selection = system.actorSelection(uri);
      final Future<ActorRef> actorFuture = selection.resolveOne(C.Global.Timeouts);
      final ActorRef result = Await.result(actorFuture, C.Global.Timeouts.duration());
      return result;
    } catch (Exception e) {
      throw new IOException(String.format("Could not resolve actor (%s) in %s!", uri, C.Global.Timeouts.duration().toString()), e);
    }
  }

  public static ActorRef createSession(
      final String sessionID,
      final boolean isRecorded) throws IOException {
    try {
      final Object sessionReq = new SessionRequest(sessionID, isRecorded);
      final Future<Object> sessionFuture = Patterns.ask(sessionFactory, sessionReq, C.Global.Timeouts);
      final ActorRef session = (ActorRef)Await.result(sessionFuture, C.Global.Timeouts.duration());
      return session;
    } catch (final Exception e) {
      throw new IOException(String.format("An error occured while trying to get a new session (%s)!", sessionID), e);
    }
  }

  public static ActorRef resumeSession(final String sessionID) throws IOException {
    return waitForActor("session-factory/" + sessionID);
  }

  public static void shutdown() {
    system.terminate();
  }
}
