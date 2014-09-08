package net.selenate.common.comms.req;

public final class SeReqSessionDestroy implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  public SeReqSessionDestroy() {
  }

  @Override
  public String toString() {
    return String.format("SeReqSessionDestroy()");
  }
}
