package net.selenate.common.comms.req;

public class SeReqSwitchFrame implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final int frame;

  public SeReqSwitchFrame(final int frame) {
    this.frame = frame;
  }
}
