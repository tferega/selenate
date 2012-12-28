package net.selenate.common.comms.res;

import java.io.Serializable;

public class SeResElementExists implements Serializable {
  private static final long serialVersionUID = 1L;

  public final boolean isFound;

  public SeResElementExists(final boolean isFound) {
    this.isFound = isFound;
  }
}
