package net.selenate.common.comms.res;

import net.selenate.common.comms.*;

public final class SeResSelectFind implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final SeSelect select;

  public SeResSelectFind(final SeSelect select) {
    this.select = select;
    validate();
  }

  public SeSelect getSelect() {
    return select;
  }

  public SeResSelectFind withSelect(final SeSelect newSelect) {
    return new SeResSelectFind(newSelect);
  }

  private void validate() {
    if (select == null) {
      throw new IllegalArgumentException("Select cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeResSelectFind(%s)", select);
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
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SeResSelectFind other = (SeResSelectFind) obj;
    if (select == null) {
      if (other.select != null)
        return false;
    } else if (!select.equals(other.select))
      return false;
    return true;
  }
}
