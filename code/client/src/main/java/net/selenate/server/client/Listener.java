package net.selenate.server.client;

import net.selenate.server.comms.res.*;
import akka.actor.UntypedActor;

public class Listener extends UntypedActor {
  public void onReceive(final Object obj) throws Exception {
    System.out.println();

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
      System.out.println("  HTML LENGTH: "+ m.html.length());
      System.out.println("  IMAGE SIZE:  "+ m.screenshot.length);
    }
    else {
      System.out.println("SERVER IS GIBBERING");
      unhandled(obj);
    }

    System.out.println();
  }
}

