package net.selenate.common.comms.req;

import net.selenate.common.comms.SeElementSelector;

public final class SeReqElementFind implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelector selector;

  public SeReqElementFind(final SeElementSelector selector) {
    this.selector = selector;
    validate();
  }

  public SeElementSelector getSelector() {
    return selector;
  }

  public SeReqElementFind withSelector(final SeElementSelector newSelector) {
    return new SeReqElementFind(newSelector);
  }

  private void validate() {
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeReqElementFind(%s)", selector);
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
    SeReqElementFind other = (SeReqElementFind) obj;
    if (selector == null) {
      if (other.selector != null)
        return false;
    } else if (!selector.equals(other.selector))
      return false;
    return true;
  }
}
