package net.selenate.common.actors;

import akka.actor.*;
import akka.util.Timeout;
import com.typesafe.config.Config;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

public class ActorFactory {
  private static final Logger logger = LoggerFactory.getLogger(ActorFactory.class);
  public static final String SYSTEM_NAME = "client";
  private static Config config;
  private static ActorFactory instance;

  public final ActorSystem system;
  private ActorFactory() {
    final String systemName = String.format("%s-system", SYSTEM_NAME);
    logger.info("Starting main actor system: {}", systemName);
    system = ActorSystem.create(systemName, config);
  }

  public static ActorFactory getInstance() {
    if (config == null) {
      throw new IllegalStateException("Config must be set before referencing actor system!");
    }
    if (instance == null) {
      instance = new ActorFactory();
    }
    return instance;
  }

  public static void setConfig(final Config config) {
    if (ActorFactory.config != null) {
      throw new IllegalStateException("Config already set!");
    }
    ActorFactory.config = config;
  }

  public ActorRef waitForActor(
      final Timeout timeout,
      final String serverSystemName,
      final String serverHost,
      final int serverPort,
      final String path) throws IOException {
    final String uri = String.format("akka.tcp://%s@%s:%d/user/%s", serverSystemName, serverHost, serverPort, path);
    logger.debug("Waiting for an actor at: {}", uri);
    try {
      final ActorSelection selection = system.actorSelection(uri);
      final Future<ActorRef> actorFuture = selection.resolveOne(timeout);
      final ActorRef result = Await.result(actorFuture, timeout.duration());
      return result;
    } catch (Exception e) {
      throw new IOException(String.format("Could not resolve actor (%s) in %s!", uri, timeout.duration().toString()), e);
    }
  }

  public ActorBuilder buildActor(
      final String name,
      final Class<? extends UntypedActor> clazz) {
    return new ActorBuilder(name, clazz);
  }

  public ActorRef createActor(
      final String name,
      final Class<? extends UntypedActor> clazz) {
    return createActor(name, clazz, null, null, null);
  }

  public ActorRef createActor(
      final String name,
      final Class<? extends UntypedActor> clazz,
      final ActorRefFactory actorContext, final String dispatcher, final Object[] args) {
    final String dispatcherStr = (dispatcher == null) ? "default dispatcher" : "dispatcher " + dispatcher;
    logger.debug("Creating actor {} using {} for class {}", name, dispatcherStr, clazz);

    final boolean isArgs = ((args != null) && (args.length > 0));
    final boolean isDispatcher = (dispatcher != null);
    final boolean isActorContext = (actorContext != null);

    final Props baseProps = isArgs ? Props.create(clazz, args) : Props.create(clazz);
    final Props props = isDispatcher ? baseProps.withDispatcher(String.format("akka.%s-dispatcher", dispatcher))
        : baseProps;
    final ActorRefFactory factory = isActorContext ? actorContext : system;

    return factory.actorOf(props, name);
  }

  public Cancellable scheduleMessage(
      final FiniteDuration delay,
      final ActorRef receiver,
      final Object message) {
    return system.scheduler().scheduleOnce(delay, receiver, message, system.dispatcher(), null);
  }

  public Runnable shutdownHook(final Timeout timeout) {
    return () -> {
      try {
        logger.info("Shutting down main client actor system");
        final Future<Terminated> termination = system.terminate();
        Await.ready(termination, timeout.duration());
      } catch (final Exception e) {
        logger.error("An error occurred while waiting for the actor system to shut down!", e);
      }
    };
  }
}
