package net.selenate.common.comms.req;

import java.io.Serializable;

public class SeReqSwitchFrame implements Serializable {
  private static final long serialVersionUID = 1L;

  public final int frame;

  public SeReqSwitchFrame(final int frame) {
    this.frame = frame;
  }
}
