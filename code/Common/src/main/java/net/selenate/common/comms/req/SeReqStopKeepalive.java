package net.selenate.common.comms.req;

public class SeReqStopKeepalive implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public SeReqStopKeepalive() {
  }

  @Override
  public String toString() {
    return String.format("SeReqStopKeepalive");
  }
}
