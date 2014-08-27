package net.selenate.common.comms.req;

public class SeReqDestroySession implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public SeReqDestroySession() {
  }

  @Override
  public String toString() {
    return String.format("SeReqDestroySession()");
  }
}
