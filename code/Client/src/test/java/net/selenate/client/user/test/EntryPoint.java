package net.selenate.client.user.test;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import net.selenate.common.comms.req.*;
import net.selenate.common.sessions.*;

import akka.actor.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class EntryPoint {
  private static ActorSystem system;
  private static ISessionFactory sessionFactory;

  private static void p(final String s) {
    System.out.println("###############==========-----> "+ s);
  }

  public static void main(String[] args) throws Throwable {
    system = ActorSystem.create("selenium-client");
    sessionFactory = TypedActor.get(system).typedActorOf(
        new TypedProps<ISessionFactory>(ISessionFactory.class),
        system.actorFor("akka://main@selenate-server:9072/user/session-factory")
    );

    final ActorRef listener = system.actorOf(new Props(Listener.class), "listener");

    BufferedReader bR = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

    Map<String, ActorRef> sessionList = new HashMap<String, ActorRef>();
    boolean end = false;
    while (!end) {
      try {
        printDoc();
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
          final ActorRef session = getSession(name);
          sessionList.put(name, session);
        }
        else if (inputElems[0].equals("act")) {
          final String name      = inputElems[1];
          final String actionStr = inputElems[2];
          ActorRef session = sessionList.get(name);

          if (session == null) {
            System.out.println(String.format("  SESSION %s NOT FOUND!", name));
          }
          else {
            final Serializable actionObj;
            if (actionStr.equals("capture")) {
              final String captureName = inputElems[3];
              actionObj = new SeReqCapture(captureName);
            }
//            else if (actionStr.equals("click")) {
//              final String xpath = inputElems[3];
//              actionObj = new SeReqClick(SeSelectMethod.XPATH, xpath);
//            }
            else if (actionStr.equals("quit")) {
              actionObj = new SeReqQuit();
            }
            else if (actionStr.equals("get")) {
              final String url = inputElems[3];
              actionObj = new SeReqGet(url);
            }
            else if (actionStr.equals("ping")) {
              actionObj = "ping";
            }
            else {
              actionObj = actionStr;
            }
            session.tell(actionObj, listener);
          }
        }
        else if (inputElems[0].equals("q")) {
          final ActorRef session = getSession("asdf");
          session.tell(new SeReqCapture(String.format("%d", System.currentTimeMillis())), listener);
        }
        else if (inputElems[0].equals("w")) {
          final ActorRef session = getSession("asdf");
          session.tell(new SeReqGet("http://users.ipa.net/~djhill/frmain.html"), listener);
        }
      } catch (Exception e) {
        System.out.println("");
        System.out.println("SOMETHIG SEEMS TO HAVE GONE WRONG");
        System.out.println("HERE ARE SOME HELPFUL DETAILS");
        System.out.println("");
        e.printStackTrace();
        System.out.println("YOU MIGHT WANT TO TRY AGAIN");
        System.out.println("");
      }
    }

    System.out.println("KTHXBAI");
    System.out.println("");
    system.shutdown();
    Runtime.getRuntime().halt(0);
  }

  private static void printDoc() {
    System.out.println("");
    System.out.println("============================================================================");
    System.out.println("COMMANDS");
    System.out.println("  end                 -> quit");
    System.out.println("  print               -> displays information about all sessions");
    System.out.println("  get %name%          -> requests a session named %name%");
    System.out.println("  act %name% %action% -> executes an action %action% on session named %name%");
    System.out.println("ACTIONS");
    System.out.println("  capture");
    System.out.println("  click %xpath%");
    System.out.println("  quit");
    System.out.println("  get %url%");
    System.out.println("  ping");
    System.out.println("");
  }

  public static ActorRef getSession(String sessionName) throws Exception {
    p("REQUESTING SESSION "+ sessionName);
    final Future<ActorRef> sessionFuture = sessionFactory.getSession(sessionName);
    final ActorRef sessionActor = Await.result(sessionFuture, Duration.create(30, SECONDS));
    p("SESSION PATH: "+ sessionActor.path());
    p("SENDING PING MESSAGE");
    sessionActor.tell("ping", sessionActor);

    return sessionActor;
  }
}
