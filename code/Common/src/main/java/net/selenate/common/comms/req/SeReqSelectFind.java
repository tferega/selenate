package net.selenate.common.comms.req;

import net.selenate.common.SelenateUtils;
import net.selenate.common.comms.*;

public final class SeReqSelectFind implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelector selector;

  public SeReqSelectFind(final SeElementSelector selector) {
    this.selector = SelenateUtils.guardNull(selector, "Selector");
  }

  public SeElementSelector getElementSelector() {
    return selector;
  }

  @Override
  public String toString() {
    return String.format("SeReqSelect(%s)", selector);
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
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeReqSelectFind other = (SeReqSelectFind) obj;
    if (selector == null) {
      if (other.selector != null) return false;
    } else if (!selector.equals(other.selector)) return false;
    return true;
  }
}