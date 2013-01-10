package net.selenate.common.comms.req;

public class SeReqGet implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String url;

  public SeReqGet(final String url) {
    if (url == null) {
      throw new IllegalArgumentException("Url cannot be null!");
    }

    this.url = url;
  }
}
