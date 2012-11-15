package net.selenate.server.comms.req;

import java.io.Serializable;

public class SeReqCapture implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String name;

  public SeReqCapture(final String name) {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null!");
    }

    this.name = name;
  }
}
