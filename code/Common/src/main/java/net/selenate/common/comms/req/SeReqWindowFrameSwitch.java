package net.selenate.common.comms.req;

import java.util.*;

import net.selenate.common.SelenateUtils;
import net.selenate.common.comms.*;

public final class SeReqWindowFrameSwitch implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelector frameSelector;

  public SeReqWindowFrameSwitch(final String frameName) {
    this(new SeElementSelector(Optional.empty(), Optional.empty(), SeElementSelectMethod.NAME, frameName));
  }

  public SeReqWindowFrameSwitch(final SeElementSelector selector) {
    this.frameSelector = SelenateUtils.guardNull(selector, "Selector");
  }

  public SeElementSelector getFrameSelector() {
    return frameSelector;
  }

  @Override
  public String toString() {
    return String.format("SeReqWindowFrameSwitch(%s)", frameSelector);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
      + ((frameSelector == null) ? 0 : frameSelector.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeReqWindowFrameSwitch other = (SeReqWindowFrameSwitch) obj;
    if (frameSelector == null) {
      if (other.frameSelector != null) return false;
    } else if (!frameSelector.equals(other.frameSelector)) return false;
    return true;
  }
}