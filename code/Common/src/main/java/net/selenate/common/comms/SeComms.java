package net.selenate.common.comms;

import java.io.Serializable;

public class SeComms implements Serializable {
  private static final long serialVersionUID = 1L;

  @Override
  public String toString() {
    return "SeComms()";
  }

  protected static String orElse(final String ... args) {
    String result = null;
    for (final String entry : args) {
      if (entry != null) {
        result = entry;
        break;
      }
    }

    return result;
  }
}
