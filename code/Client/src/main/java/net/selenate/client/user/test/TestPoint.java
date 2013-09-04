package net.selenate.client.user.test;

import akka.actor.ActorRef;
import net.selenate.client.user.ActorBrowser;
import net.selenate.client.user.ActorFactory;
import net.selenate.client.user.test.HandelBussinesScraper;

public class TestPoint {
  public static void main(String[] args) throws Exception {
    try {
      final ActorRef session = ActorFactory.getSession("b", 30);
      ActorBrowser browser = new ActorBrowser(session);
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      ActorFactory.shutdown();
    }
  }
}
