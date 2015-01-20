package net.selenate.common.comms.req;

import net.selenate.common.SelenateUtils;
import net.selenate.common.comms.SeElementSelector;


public class SeReqElementSetAttribute implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelector selector;
  private final String            attributeName;
  private final String            attributeValue;

  public SeReqElementSetAttribute(
    final SeElementSelector selector,
    final String attributeName,
    final String attributeValue)
  {
    this.selector = SelenateUtils.guardNull(selector, "Selector");
    this.attributeName = SelenateUtils.guardNullOrEmpty(attributeName, "AttributeName");
    this.attributeValue = attributeValue;
  }

  public SeElementSelector getSelector() {
    return selector;
  }

  public String getAttributeName() {
    return attributeName;
  }

  public String getAttributeValue() {
    return attributeValue;
  }

  @Override
  public String toString() {
    return String.format("SeReqElementSetAttribute(%s, %s = %s)", selector, attributeName, attributeValue);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
      + ((attributeName == null) ? 0 : attributeName.hashCode());
    result = prime * result
      + ((attributeValue == null) ? 0 : attributeValue.hashCode());
    result = prime * result + ((selector == null) ? 0 : selector.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeReqElementSetAttribute other = (SeReqElementSetAttribute) obj;
    if (attributeName == null) {
      if (other.attributeName != null) return false;
    } else if (!attributeName.equals(other.attributeName)) return false;
    if (attributeValue == null) {
      if (other.attributeValue != null) return false;
    } else if (!attributeValue.equals(other.attributeValue)) return false;
    if (selector == null) {
      if (other.selector != null) return false;
    } else if (!selector.equals(other.selector)) return false;
    return true;
  }
}