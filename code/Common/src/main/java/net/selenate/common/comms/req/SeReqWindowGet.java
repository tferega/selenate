package net.selenate.common.comms.req;

import java.net.URL;

import net.selenate.common.SelenateUtils;
import net.selenate.common.exceptions.SeInvalidArgumentException;

public final class SeReqWindowGet implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final String url;

  public SeReqWindowGet(final String url) {
    this.url = SelenateUtils.guardNull(url, "URL");

    try {
      new URL(url);
    } catch (final Exception e) {
      throw new SeInvalidArgumentException("An error occured while interpreting url as java.net.URL!", e);
    }
  }

  public String getUrl() {
    return url;
  }

  public SeReqWindowGet withUrl(final String newUrl) {
    return new SeReqWindowGet(newUrl);
  }

  @Override
  public String toString() {
    return String.format("SeReqWindowOpen(%s)", url);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((url == null) ? 0 : url.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SeReqWindowGet other = (SeReqWindowGet) obj;
    if (url == null) {
      if (other.url != null)
        return false;
    } else if (!url.equals(other.url))
      return false;
    return true;
  }
}