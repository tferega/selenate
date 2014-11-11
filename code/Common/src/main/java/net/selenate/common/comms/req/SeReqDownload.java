package net.selenate.common.comms.req;

import java.util.Map;

import net.selenate.common.comms.SeDownloadMethod;

public class SeReqDownload implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String url;
  public final SeDownloadMethod method;
  public final Map<String, String> headers;
  public final byte[] body;

  public SeReqDownload(final String url, final SeDownloadMethod method, final Map<String, String> headers, final byte[] body) {
    if (url == null) {
      throw new IllegalArgumentException("Url cannot be null!");
    }

    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }

    if (headers == null) {
      throw new IllegalArgumentException("Headers cannot be null!");
    }

    this.url     = url;
    this.method  = method;
    this.headers = headers;
    this.body    = body;
  }

  @Override
  public String toString() {
    return String.format("SeReqDownload(%s, %s, %d, %d)", url, method, headers.size(), body.length);
  }
}
