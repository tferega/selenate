package net.selenate.common.comms.req;

import net.selenate.common.user.Cookie;

public class SeReqAddCookie implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final Cookie cookie;

  public SeReqAddCookie(final Cookie cookie) {
    if (cookie == null) {
      throw new IllegalArgumentException("Cookie cannot be null!");
    }

    this.cookie = cookie;
  }

  @Override
  public String toString() {
    return String.format("SeReqAddCookie(Cookie = [%s])", cookie.toString());
  }
}
