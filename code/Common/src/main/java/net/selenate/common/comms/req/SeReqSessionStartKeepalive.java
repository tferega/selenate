package net.selenate.common.comms.req;

public final class SeReqSessionStartKeepalive implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  public SeReqSessionStartKeepalive() {
  }

  @Override
  public String toString() {
    return String.format("SeReqStartKeepalive()");
  }
}