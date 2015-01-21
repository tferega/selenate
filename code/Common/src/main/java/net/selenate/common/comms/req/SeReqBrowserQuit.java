package net.selenate.common.comms.req;

public final class SeReqBrowserQuit implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  public SeReqBrowserQuit() {
  }

  @Override
  public String toString() {
    return String.format("SeReqBrowserQuit()");
  }
}