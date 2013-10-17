package net.selenate.common.comms.req;

public class SeReqAddCookieNamed implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String name;
  public final String value;

  public SeReqAddCookieNamed(final String name, final String value) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }
    if (value == null) {
      throw new IllegalArgumentException("Value cannot be null!");
    }

    this.name = name;
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("SeReqAddCookieNamed(%s, %s)", name, value);
  }
}
