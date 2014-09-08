package net.selenate.common.comms.req;

import net.selenate.common.comms.SeElementSelector;

public final class SeReqElementTextClear implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelector selector;

  public SeReqElementTextClear(final SeElementSelector selector) {
    this.selector  = selector;
    validate();
  }

  public SeElementSelector getSelector() {
    return selector;
  }

  public SeReqElementTextClear withSelector(final SeElementSelector newSelector) {
    return new SeReqElementTextClear(newSelector);
  }

  private void validate() {
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeReqElementTextClear(%s)", selector);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((selector == null) ? 0 : selector.hashCode());
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
    SeReqElementTextClear other = (SeReqElementTextClear) obj;
    if (selector == null) {
      if (other.selector != null)
        return false;
    } else if (!selector.equals(other.selector))
      return false;
    return true;
  }
}
