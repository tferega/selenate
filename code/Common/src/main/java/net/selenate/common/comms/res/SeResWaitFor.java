package net.selenate.common.comms.res;

import java.io.Serializable;

public class SeResWaitFor implements Serializable {
  private static final long serialVersionUID = 1L;

  public final boolean isSuccessful;

  public SeResWaitFor(final boolean isSuccessful) {
    this.isSuccessful = isSuccessful;
  }

  @Override
  public String toString() {
    return String.format("SeResWaitFor [%s]", isSuccessful ? "successful" : "failed");
  }
}
