package net.selenate.common.comms.req;

public final class SeReqSessionStopKeepalive implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  public SeReqSessionStopKeepalive() {
  }

  @Override
  public String toString() {
    return String.format("SeReqSessionStopKeepalive()");
  }
}
