package net.selenate.common.comms.req;

public class SeReqClose implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String windowHandle;

  public SeReqClose(final String windowHandle) {
    if (windowHandle == null) {
      throw new IllegalArgumentException("Window handle cannot be null!");
    }

    this.windowHandle = windowHandle;
  }
}
