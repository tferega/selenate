package net.selenate.server.client;

import net.selenate.server.actions.*;

import akka.actor.*;

public class EntryPoint {
  private static ActorSystem system;
  private static ISessionFactory sessionFactory;

  private static void p(final String s) {
    System.out.println("###############==========-----> "+ s);
  }

  public static void main(String[] args) {
    system = ActorSystem.create("selenium-server-client");
    sessionFactory = TypedActor.get(system).typedActorOf(
        new TypedProps<ISessionFactory>(ISessionFactory.class),
        system.actorFor("akka://selenate-server@localhost:9070/user/session-factory")
    );

    test("new1");
    test("new2");
  }

  public static void test(String sessionName) {
    p("REQUESTING SESSION PATH ("+ sessionName +")");
    String sessionActorName = sessionFactory.getSession(sessionName);
    p("SESSION NAME: "+ sessionActorName);

    p("REQUESTING SESSION ACTOR");
    ActorRef sessionActor = system.actorFor("akka://selenate-server@localhost:9070/user/"+ sessionActorName);
    p("SENDING MESSAGE");
    sessionActor.tell("test");
  }
}
