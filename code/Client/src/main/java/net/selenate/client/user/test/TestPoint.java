package net.selenate.client.user.test;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.util.Timeout;
import net.selenate.client.user.ActorBrowser;
import net.selenate.client.user.ActorFactory;
import net.selenate.client.user.test.HandelBussinesScraper;

public class TestPoint {
  public static void main(String[] args) throws Exception {
    try {
      final ActorRef session = ActorFactory.getSession("b", 30);
      ActorBrowser browser = new ActorBrowser(session);
      browser.setTimeout(new Timeout(60, TimeUnit.SECONDS));
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      ActorFactory.shutdown();
    }
  }
}
