package net.selenate.client.user.test;

import java.util.UUID;

import akka.actor.ActorRef;
import net.selenate.client.user.ActorBrowser;
import net.selenate.client.user.ActorFactory;

public class TestPoint {
  public static void main(String[] args) throws Exception {
    try {
      final UUID sessionID = java.util.UUID.randomUUID();
      final ActorRef session = ActorFactory.getSession(sessionID.toString(), 30);
      ActorBrowser browser = new ActorBrowser(session);

      browser.open("http://www.w3schools.com/htmL/html_iframe.asp");
      browser.setConfigureS3Client("testRealm");
      browser.capture("test");
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      ActorFactory.shutdown();
    }
  }
}
