package net.selenate.common.comms.req;

public final class SeReqWindowFrameReset implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  public SeReqWindowFrameReset() {
  }

  @Override
  public String toString() {
    return String.format("SeReqWindowResetFrame()");
  }
}
