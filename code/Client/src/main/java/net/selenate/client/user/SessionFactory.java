package net.selenate.client.user;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;
import java.io.IOException;
import net.selenate.common.actors.ActorFactory;
import net.selenate.common.sessions.SessionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;

public class SessionFactory {
  private static final Logger logger = LoggerFactory.getLogger(SessionFactory.class);

  private final Timeout timeout;
  private final String serverSystemName;
  private final String serverHost;
  private final int serverPort;
  private final ActorRef sessionFactory;

  public SessionFactory(
      final Timeout timeout,
      final String serverSystemName,
      final String serverHost,
      final int serverPort) throws IOException {
    this.timeout = timeout;
    this.serverSystemName = serverSystemName;
    this.serverHost = serverHost;
    this.serverPort = serverPort;
    this.sessionFactory = ActorFactory.getInstance().waitForActor(timeout, serverSystemName, serverHost, serverPort, "session-factory");
  }

  public Session createSession(final String sessionId) throws IOException {
    logger.info("Creating a new session actor for id: {}", sessionId);
    final ActorRef actor = createSessionActor(sessionId);
    return initializeSession(sessionId, actor);
  }

  public Session resumeSession(final String sessionId) throws IOException {
    logger.info("Resuming a session actor with id: {}", sessionId);
    final ActorRef actor = resumeSessionActor(sessionId);
    return initializeSession(sessionId, actor);
  }

  public Session resumeOrCreateSession(final String sessionId) throws IOException {
    logger.info("Creating or resuming a session actor with id: {}", sessionId);
    ActorRef actor;
    try {
      actor = resumeSessionActor(sessionId);
    } catch (final Exception e) {
      actor = createSessionActor(sessionId);
    }
    return initializeSession(sessionId, actor);
  }

  private ActorRef createSessionActor(final String sessionId) throws IOException {
    try {
      final Object sessionReq = new SessionRequest(sessionId, false);
      final Future<Object> sessionFuture = Patterns.ask(sessionFactory, sessionReq, timeout);
      final ActorRef session = (ActorRef)Await.result(sessionFuture, timeout.duration());
      return session;
    } catch (final Exception e) {
      throw new IOException(String.format("An error occurred while trying to create a new session actor (%s)!", sessionId), e);
    }
  }

  private ActorRef resumeSessionActor(final String sessionId) throws IOException {
    try {
      final ActorRef session = ActorFactory.getInstance().waitForActor(timeout, serverSystemName, serverHost, serverPort, "session-factory/" + sessionId);
      return session;
    } catch (final Exception e) {
      throw new IOException(String.format("An error occurred while trying to resume existing session (%s)!", sessionId), e);
    }
  }

  private Session initializeSession(
      final String sessionId,
      final ActorRef sessionActor) throws IOException {
    try {
      logger.info("Initializing session id: {}", sessionId);
      final Browser browser = new Browser(timeout, sessionActor);
      return new Session(sessionId, sessionActor, browser);
    } catch (final Exception e) {
      throw new IOException(String.format("An error occurred while trying to initialize session (%s)!", sessionId), e);
    }
  }
}
