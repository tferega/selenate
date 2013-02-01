package net.selenate.common.comms.req;

public class SeReqDownload implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String url;

  public SeReqDownload(final String url) {
    if (url == null) {
      throw new IllegalArgumentException("Url cannot be null!");
    }

    this.url = url;
  }

  @Override
  public String toString() {
    return String.format("SeReqDownload(%s)", url);
  }
}
