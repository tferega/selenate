package net.selenate.common.comms.req;

import net.selenate.common.SelenateUtils;

public final class SeReqCookieDeleteNamed implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final String name;

  public SeReqCookieDeleteNamed(final String name) {
    this.name = SelenateUtils.guardNullOrEmpty(name, "Name");
  }

  public String getName() {
    return name;
  }

  public SeReqCookieDeleteNamed withName(final String newName) {
    return new SeReqCookieDeleteNamed(newName);
  }

  @Override
  public String toString() {
    return String.format("SeReqCookieDeleteNamed(%s)", name);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    SeReqCookieDeleteNamed other = (SeReqCookieDeleteNamed) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
}