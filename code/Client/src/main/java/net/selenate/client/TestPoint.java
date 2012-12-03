package net.selenate.client;

import java.io.IOException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Timeout;
import java.util.concurrent.TimeUnit;
import akka.actor.*;
import net.selenate.client.user.ActorBrowser;
import net.selenate.common.comms.req.*;
import net.selenate.common.sessions.*;


public class TestPoint {
  public static final Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
  public static ActorRef session = null;

  public static void main(String[] args) throws Exception {
    ActorSystem system = ActorSystem.create("selenium-client");
    ISessionFactory sessionFactory = sessionFactory = TypedActor.get(system).typedActorOf(
        new TypedProps<ISessionFactory>(ISessionFactory.class),
        system.actorFor("akka://main@selenate-server:9070/user/session-factory")
    );


    try {
      String sessionActorPath = sessionFactory.getSession("a");
      session = system.actorFor(sessionActorPath);
      ActorBrowser b = new ActorBrowser(session);

//      b.open("http://www.google.com");
      b.quit();

    } finally {
      system.shutdown();
    }
  }
}
