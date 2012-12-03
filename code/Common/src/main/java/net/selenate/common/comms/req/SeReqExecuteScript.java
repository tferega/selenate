package net.selenate.common.comms.req;

import java.io.Serializable;

public class SeReqExecuteScript implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String javascript;

  public SeReqExecuteScript(final String javascript) {
    if (javascript == null) {
      throw new IllegalArgumentException("Javascript cannot be null!");
    }

    this.javascript = javascript;
  }
}
