package net.selenate.common.comms.req;

import net.selenate.common.SelenateUtils;
import net.selenate.common.comms.*;

public final class SeReqSelectChoose implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelector parentSelector;
  private final SeOptionSelector  optionSelector;

  public SeReqSelectChoose(
      final SeElementSelector parentSelector,
      final SeOptionSelector  optionSelector) {
    this.parentSelector = SelenateUtils.guardNull(parentSelector, "ParentSelector");
    this.optionSelector = SelenateUtils.guardNull(optionSelector, "OptionSelector");
  }

  public SeElementSelector getParentSelector() {
    return parentSelector;
  }

  public SeOptionSelector getOptionSelector() {
    return optionSelector;
  }

  public SeReqSelectChoose withParentSelector(final SeElementSelector newParentSelector) {
    return new SeReqSelectChoose(newParentSelector, this.optionSelector);
  }

  public SeReqSelectChoose withOptionSelector(final SeOptionSelector newOptionSelector) {
    return new SeReqSelectChoose(this.parentSelector, newOptionSelector);
  }

  @Override
  public String toString() {
    return String.format("SeReqSelectOption(%s, %s)",
        parentSelector, optionSelector);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((optionSelector == null) ? 0 : optionSelector.hashCode());
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
    SeReqSelectChoose other = (SeReqSelectChoose) obj;
    if (optionSelector == null) {
      if (other.optionSelector != null)
        return false;
    } else if (!optionSelector.equals(other.optionSelector))
      return false;
    if (parentSelector == null) {
      if (other.parentSelector != null)
        return false;
    } else if (!parentSelector.equals(other.parentSelector))
      return false;
    return true;
  }
}
