package net.selenate.common.comms.req;

import net.selenate.common.comms.SeElementSelector;
import net.selenate.common.exceptions.SeNullArgumentException;

public final class SeReqElementFindList implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelector parentSelector;
  private final SeElementSelector targetSelector;

  public SeReqElementFindList(final SeElementSelector targetSelector) {
    this(null, targetSelector);
  }

  public SeReqElementFindList(
      final SeElementSelector parentSelector,
      final SeElementSelector targetSelector) {
    this.parentSelector = parentSelector;
    this.targetSelector = targetSelector;
    validate();
  }

  public SeElementSelector getParentSelector() {
    return parentSelector;
  }

  public SeElementSelector getTargetSelector() {
    return targetSelector;
  }

  public SeReqElementFindList withParentSelector(final SeElementSelector newParentSelector) {
    return new SeReqElementFindList(newParentSelector, this.targetSelector);
  }

  public SeReqElementFindList withTargetSelector(final SeElementSelector newTargetSelector) {
    return new SeReqElementFindList(this.parentSelector, newTargetSelector);
  }

  private void validate() {
    if (targetSelector == null) {
      throw new SeNullArgumentException("Target selector");
    }
  }

  @Override
  public String toString() {
    return String.format("SeReqElementFindList(%s, %s)",
        parentSelector, targetSelector);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((targetSelector == null) ? 0 : targetSelector.hashCode());
    result = prime * result
        + ((parentSelector == null) ? 0 : parentSelector.hashCode());
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
    SeReqElementFindList other = (SeReqElementFindList) obj;
    if (targetSelector == null) {
      if (other.targetSelector != null)
        return false;
    } else if (!targetSelector.equals(other.targetSelector))
      return false;
    if (parentSelector == null) {
      if (other.parentSelector != null)
        return false;
    } else if (!parentSelector.equals(other.parentSelector))
      return false;
    return true;
  }
}
