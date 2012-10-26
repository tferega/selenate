package net.selenate.server.client;

import net.selenate.server.actions.*;

import akka.actor.*;

public class EntryPoint {
  public static void main(String[] args) {
    final ActorSystem system = ActorSystem.create("selenium-server-client");

    IAction client = TypedActor.get(system).typedActorOf(
        new TypedProps<IAction>(IAction.class),
        system.actorFor("akka://selenate-server@localhost:9070/user/ss")
    );

    boolean b = client.get("http://www.google.com");
    System.out.println("RETURNED: "+ b);
  }
}
