package net.selenate.client.user.test;

import akka.actor.ActorRef;
import net.selenate.client.user.ActorBrowser;
import net.selenate.client.user.ActorFactory;
import net.selenate.client.user.test.HandelBussinesScraper;

public class TestPoint {
  public static void main(String[] args) throws Exception {
    try {
      final ActorRef session = ActorFactory.getSession("b");
      ActorBrowser browser = new ActorBrowser(session);

      final HandelBussinesScraper scraper = new HandelBussinesScraper(browser);
      String challenge = scraper.start();
      System.out.println(challenge);
      scraper.login("12341234", "12341234");
    } finally {
      ActorFactory.shutdown();
    }
  }
}
