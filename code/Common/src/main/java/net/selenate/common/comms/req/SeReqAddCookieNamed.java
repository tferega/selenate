package net.selenate.common.comms.req;

public class SeReqAddCookieNamed implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String name;
  public final String cookie;

  public SeReqAddCookieNamed(final String name, final String cookie) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }
    if (cookie == null) {
      throw new IllegalArgumentException("Cookie cannot be null!");
    }

    this.name = name;
    this.cookie = cookie;
  }

  @Override
  public String toString() {
    return String.format("SeReqDeleteCookieNamed(%s, %s)", name, cookie);
  }
}
