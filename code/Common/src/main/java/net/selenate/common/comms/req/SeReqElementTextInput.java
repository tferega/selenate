package net.selenate.common.comms.req;

import net.selenate.common.SelenateUtils;
import net.selenate.common.comms.SeElementSelector;

public final class SeReqElementTextInput implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelector selector;
  private final boolean           isAppend;
  private final String            text;

  public SeReqElementTextInput(
      final SeElementSelector selector,
      final boolean           isAppend,
      final String            text) {
    this.selector  = SelenateUtils.guardNull(selector, "Selector");
    this.isAppend  = isAppend;
    this.text      = SelenateUtils.guardNull(text, "Text");
  }

  public SeElementSelector getSelector() {
    return selector;
  }

  public boolean isAppend() {
    return isAppend;
  }

  public String getText() {
    return text;
  }

  public SeReqElementTextInput withSelector(final SeElementSelector newSelector) {
    return new SeReqElementTextInput(newSelector, this.isAppend, this.text);
  }

  public SeReqElementTextInput withAppend(final boolean newIsAppend) {
    return new SeReqElementTextInput(this.selector, newIsAppend, this.text);
  }

  public SeReqElementTextInput withText(final String newText) {
    return new SeReqElementTextInput(this.selector, this.isAppend, newText);
  }

  @Override
  public String toString() {
    return String.format("SeReqElementTextInput(%s, %b, %s)",
        selector, isAppend, text);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (isAppend ? 1231 : 1237);
    result = prime * result + ((selector == null) ? 0 : selector.hashCode());
    result = prime * result + ((text == null) ? 0 : text.hashCode());
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
    SeReqElementTextInput other = (SeReqElementTextInput) obj;
    if (isAppend != other.isAppend)
      return false;
    if (selector == null) {
      if (other.selector != null)
        return false;
    } else if (!selector.equals(other.selector))
      return false;
    if (text == null) {
      if (other.text != null)
        return false;
    } else if (!text.equals(other.text))
      return false;
    return true;
  }
}