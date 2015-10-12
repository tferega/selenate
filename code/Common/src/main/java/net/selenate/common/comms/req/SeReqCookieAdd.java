package net.selenate.common.comms.req;

import net.selenate.common.SelenateUtils;
import net.selenate.common.comms.SeCookie;

public final class SeReqCookieAdd implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeCookie cookie;

  public SeReqCookieAdd(final SeCookie cookie) {
    this.cookie = SelenateUtils.guardNull(cookie, "Cookie");
  }

  public SeCookie getCookie() {
    return cookie;
  }

  public SeReqCookieAdd withCookie(final SeCookie newCookie) {
    return new SeReqCookieAdd(newCookie);
  }

  @Override
  public String toString() {
    return String.format("SeReqCookieAdd(%s)", cookie);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cookie == null) ? 0 : cookie.hashCode());
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
    SeReqCookieAdd other = (SeReqCookieAdd) obj;
    if (cookie == null) {
      if (other.cookie != null)
        return false;
    } else if (!cookie.equals(other.cookie))
      return false;
    return true;
  }
}