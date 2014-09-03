package net.selenate.common.comms.req;

import net.selenate.common.comms.SeCookie;

public class SeReqAddCookie implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final SeCookie cookie;

  public SeReqAddCookie(final SeCookie cookie) {
    if (cookie == null) {
      throw new IllegalArgumentException("Cookie cannot be null!");
    }

    this.cookie = cookie;
  }

  @Override
  public String toString() {
    return String.format("SeReqAddCookie(%s)", cookie);
  }
}
