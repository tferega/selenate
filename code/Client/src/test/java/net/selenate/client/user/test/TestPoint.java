package net.selenate.client.user.test;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.util.Timeout;
import net.selenate.client.user.ActorBrowser;
import net.selenate.client.user.ActorFactory;
import net.selenate.common.user.*;

// TODO: findelementaction, waitforelement spec. search for non existent, and check if failed under specified timeout!
public class TestPoint {
  public static void main(String[] args) throws Exception {
    try {
      final ActorRef session = ActorFactory.getSession("b12121", 30);
      ActorBrowser browser = new ActorBrowser(session);
      browser.setTimeout(new Timeout(60, TimeUnit.SECONDS));
      browser.open("http://datahub.io/dataset/iso-3166-1-alpha-2-country-codes/resource/9c3b30dd-f5f3-4bbe-a3cb-d7b2c21d66ce");
//      browser.download(new ElementSelector(ElementSelectMethod.XPATH, "//a[@title='https://commondatastorage.googleapis.com/ckannet-storage/2011-11-25T132653/iso_3166_2_countries.csv']"));

      // browser.download(ElementSelector).... should return binary

    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      //ActorFactory.shutdown();
    }
  }
}
