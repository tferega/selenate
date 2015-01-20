package net.selenate.common.comms.res;

import net.selenate.common.SelenateUtils;
import net.selenate.common.comms.*;

public final class SeResSelect implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final SeSelect select;

  public SeResSelect(final SeSelect select) {
    this.select = SelenateUtils.guardNull(select, "Select");
  }

  public SeSelect getSelect() {
    return select;
  }

  @Override
  public String toString() {
    return String.format("SeResSelect(%s)", select);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((select == null) ? 0 : select.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeResSelect other = (SeResSelect) obj;
    if (select == null) {
      if (other.select != null) return false;
    } else if (!select.equals(other.select)) return false;
    return true;
  }
}