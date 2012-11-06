package net.selenate.server.client;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import net.selenate.server.actions.*;

import akka.actor.*;

public class EntryPoint {
  private static ActorSystem system;
  private static ISessionFactory sessionFactory;

  private static void p(final String s) {
    System.out.println("###############==========-----> "+ s);
  }

  public static void main(String[] args) throws Throwable {
    system = ActorSystem.create("selenium-server-client");
    sessionFactory = TypedActor.get(system).typedActorOf(
        new TypedProps<ISessionFactory>(ISessionFactory.class),
        system.actorFor("akka://selenate-server@localhost:9070/user/session-factory")
    );

    BufferedReader bR = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

    Map<String, ActorRef> sessionList = new HashMap<String, ActorRef>();
    boolean end = false;
    while (!end) {
      System.out.println("");
      System.out.println("============================================================================");
      System.out.println("COMMANDS");
      System.out.println("  end                 -> quit");
      System.out.println("  print               -> displays information about all sessions");
      System.out.println("  get %name%          -> requests a session named %name%");
      System.out.println("  act %name% %action% -> executes an action %action% on session named %name%");
      System.out.println("");
      System.out.print("ENTER COMMAND: ");
      final String input = bR.readLine();

      final String[] inputElems = input.split(" ");
      if (inputElems[0].equals("end")) {
        end = true;
      }
      else if (inputElems[0].equals("print")) {
        for (Map.Entry<String, ActorRef> session : sessionList.entrySet()) {
          System.out.println(String.format("  %s", session.getKey()));
        }
      }
      else if (inputElems[0].equals("get")) {
        final String name = inputElems[1];
        ActorRef session = getSession(name);
        sessionList.put(name, session);
      }
      else if (inputElems[0].equals("act")) {
        final String name   = inputElems[1];
        final String action = inputElems[2];
        ActorRef session = sessionList.get(name);

        if (session == null) {
          System.out.println(String.format("  SESSION %s NOT FOUND!", name));
        }
        else {
          session.tell(action);
        }
      }
    }

    System.out.println("KTHXBAI");
    System.out.println("");
    system.shutdown();
    Runtime.getRuntime().halt(0);
  }

  public static ActorRef getSession(String sessionName) {
    p("REQUESTING SESSION "+ sessionName);
    String sessionActorName = sessionFactory.getSession(sessionName);
    ActorRef sessionActor = system.actorFor("akka://selenate-server@localhost:9070/user/"+ sessionActorName);
    p("SESSION NAME: "+ sessionActorName);
    p("SENDING PING MESSAGE");
    sessionActor.tell("ping");

    return sessionActor;
  }
}
