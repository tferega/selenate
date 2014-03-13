/*
package net.selenate.client.user.test;

import net.selenate.common.sessions.*;
import akka.actor.ActorRef;
import net.selenate.client.user.ActorBrowser;
import net.selenate.client.user.ActorFactory;
import net.selenate.client.user.test.HandelBussinesScraper;

public class TestPoint {
  public static void main(String[] args) throws Exception {
    try {
      SessionOptions options = new SessionOptions();
      final ActorRef session = ActorFactory.getSession("b", options, 30);
      ActorBrowser browser = new ActorBrowser(session);
      Thread.sleep(10000);
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      ActorFactory.shutdown();
    }
  }
}
*/
