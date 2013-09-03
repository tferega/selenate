package net.selenate.client.user.test;

import net.selenate.common.comms.res.*;
import akka.actor.UntypedActor;

public class Listener extends UntypedActor {
  public void onReceive(final Object obj) throws Exception {
    System.out.println();

    System.out.println("A MESSAGE!");
    if (obj instanceof String) {
      System.out.println("SERVER RESPONDS WITH A MESSAGE");
      final String m = (String) obj;
      System.out.println(m);
    }
    else if (obj.getClass().isAssignableFrom(Exception.class)) {
      System.out.println("SERVER RESPONDS WITH AN ERROR");
      throw (Exception) obj;
    }
    else if (obj instanceof SeResCapture) {
      System.out.println("SERVER RESPONDS WITH A CAPUTRE");
      final SeResCapture m = (SeResCapture) obj;
      SaveCapture.saveCapture(m);
    }
    else {
      System.out.println("SERVER IS GIBBERING (" + obj.toString() + ")");
      unhandled(obj);
    }

    System.out.println();
  }
}
