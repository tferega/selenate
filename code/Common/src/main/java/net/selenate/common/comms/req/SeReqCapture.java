package net.selenate.common.comms.req;

public class SeReqCapture extends SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String name;

  public SeReqCapture(final String name) {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null!");
    }

    this.name = name;
  }
}
