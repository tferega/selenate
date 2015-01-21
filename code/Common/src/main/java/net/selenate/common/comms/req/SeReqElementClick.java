package net.selenate.common.comms.req;

import net.selenate.common.SelenateUtils;
import net.selenate.common.comms.SeElementSelector;

public final class SeReqElementClick implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelector selector;

  public SeReqElementClick(final SeElementSelector selector) {
    this.selector = SelenateUtils.guardNull(selector, "Selector");
  }

  public SeElementSelector getSelector() {
    return selector;
  }

  public SeReqElementClick withSelector(final SeElementSelector newSelector) {
    return new SeReqElementClick(newSelector);
  }

  @Override
  public String toString() {
    return String.format("SeReqElementClick(%s)", selector);
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
    SeReqElementClick other = (SeReqElementClick) obj;
    if (selector == null) {
      if (other.selector != null)
        return false;
    } else if (!selector.equals(other.selector))
      return false;
    return true;
  }
}