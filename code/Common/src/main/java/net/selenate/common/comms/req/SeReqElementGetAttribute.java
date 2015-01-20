package net.selenate.common.comms.req;

import net.selenate.common.SelenateUtils;
import net.selenate.common.comms.SeElementSelector;

public class SeReqElementGetAttribute implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelector selector;
  private final String            attributeName;

  public SeReqElementGetAttribute(final SeElementSelector selector, final String attributeName) {
    this.selector = SelenateUtils.guardNull(selector, "Selector");
    this.attributeName = SelenateUtils.guardNullOrEmpty(attributeName, "AttributeName");
  }

  public SeElementSelector getSelector() {
    return selector;
  }

  public String getAttributeName() {
    return attributeName;
  }

  @Override
  public String toString() {
    return String.format("SeReqElementGetAttribute(%s, %s)", selector, attributeName);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
      + ((attributeName == null) ? 0 : attributeName.hashCode());
    result = prime * result + ((selector == null) ? 0 : selector.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeReqElementGetAttribute other = (SeReqElementGetAttribute) obj;
    if (attributeName == null) {
      if (other.attributeName != null) return false;
    } else if (!attributeName.equals(other.attributeName)) return false;
    if (selector == null) {
      if (other.selector != null) return false;
    } else if (!selector.equals(other.selector)) return false;
    return true;
  }
}