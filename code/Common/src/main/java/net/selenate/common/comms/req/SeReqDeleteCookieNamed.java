package net.selenate.common.comms.req;

public class SeReqDeleteCookieNamed implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String name;

  public SeReqDeleteCookieNamed(final String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }

    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("SeReqDeleteCookieNamed(%s)", name);
  }
}
