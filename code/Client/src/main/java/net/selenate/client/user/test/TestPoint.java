package net.selenate.client.user.test;

import net.selenate.common.user.Preferences;

import net.selenate.common.user.Options;
import akka.actor.ActorRef;
import net.selenate.client.user.ActorBrowser;
import net.selenate.client.user.ActorFactory;
import net.selenate.client.user.test.HandelBussinesScraper;

public class TestPoint {
  public static void main(String[] args) throws Exception {
    try {
      Options options = new Options(new Preferences(), false, true, null);
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
