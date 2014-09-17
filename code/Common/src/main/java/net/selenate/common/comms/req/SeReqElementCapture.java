package net.selenate.common.comms.req;

import net.selenate.common.comms.SeElementSelector;
import net.selenate.common.exceptions.SeNullArgumentException;

public final class SeReqElementCapture implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelector selector;

  public SeReqElementCapture(final SeElementSelector selector) {
    this.selector = selector;
    validate();
  }

  public SeElementSelector getSelector() {
    return selector;
  }

  public SeReqElementCapture withSelector(final SeElementSelector newSelector) {
    return new SeReqElementCapture(newSelector);
  }

  private void validate() {
    if (selector == null) {
      throw new SeNullArgumentException("Selector");
    }
  }

  @Override
  public String toString() {
    return String.format("SeReqElementCapture(%s)", selector);
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
    SeReqElementCapture other = (SeReqElementCapture) obj;
    if (selector == null) {
      if (other.selector != null)
        return false;
    } else if (!selector.equals(other.selector))
      return false;
    return true;
  }
}
