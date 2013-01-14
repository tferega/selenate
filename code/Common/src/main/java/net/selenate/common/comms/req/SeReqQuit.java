package net.selenate.common.comms.req;

public class SeReqQuit implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public SeReqQuit() {
  }

  @Override
  public String toString() {
    return String.format("SeReqQuit()");
  }
}
