package net.selenate.common.comms.req;

import java.util.*;

import net.selenate.common.SelenateUtils;
import net.selenate.common.comms.*;

public final class SeReqWindowFrameSwitch implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  public static enum FrameSwitchMethod {
    PATH,
    NAME,
    SELECTOR
  }

  private final FrameSwitchMethod method;
  private final List<Integer>     framePath;
  private final String            frameName;
  private final SeElementSelector frameSelector;

  public SeReqWindowFrameSwitch(final List<Integer> framePath) {
    this.method        = FrameSwitchMethod.PATH;
    this.framePath     = SelenateUtils.guardNull(framePath, "FramePath");
    this.frameName     = null;
    this.frameSelector = null;
  }

  public SeReqWindowFrameSwitch(final String frameName) {
    this.method        = FrameSwitchMethod.NAME;
    this.framePath     = null;
    this.frameName     = SelenateUtils.guardNullOrEmpty(frameName, "FrameName");
    this.frameSelector = null;
  }

  public SeReqWindowFrameSwitch(final SeElementSelector selector) {
    this.method        = FrameSwitchMethod.SELECTOR;
    this.framePath     = null;
    this.frameName     = null;
    this.frameSelector = SelenateUtils.guardNull(selector, "Selector");
  }

  public FrameSwitchMethod getMethod() {
    return method;
  }

  public List<Integer> getFramePath() {
    if (method != FrameSwitchMethod.PATH) throw new UnsupportedOperationException();
    return framePath;
  }

  public String getFrameName() {
    if (method != FrameSwitchMethod.NAME) throw new UnsupportedOperationException();
    return frameName;
  }

  public SeElementSelector getFrameSelector() {
    if (method != FrameSwitchMethod.SELECTOR) throw new UnsupportedOperationException();
    return frameSelector;
  }

  @Override
  public String toString() {
    return String.format("SeReqWindowFrameSwitch(%s, %s, %s, %s)",
      method, SelenateUtils.listToString(framePath), frameName, frameSelector);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((frameName == null) ? 0 : frameName.hashCode());
    result = prime * result + ((framePath == null) ? 0 : framePath.hashCode());
    result = prime * result
      + ((frameSelector == null) ? 0 : frameSelector.hashCode());
    result = prime * result + ((method == null) ? 0 : method.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeReqWindowFrameSwitch other = (SeReqWindowFrameSwitch) obj;
    if (frameName == null) {
      if (other.frameName != null) return false;
    } else if (!frameName.equals(other.frameName)) return false;
    if (framePath == null) {
      if (other.framePath != null) return false;
    } else if (!framePath.equals(other.framePath)) return false;
    if (frameSelector == null) {
      if (other.frameSelector != null) return false;
    } else if (!frameSelector.equals(other.frameSelector)) return false;
    if (method != other.method) return false;
    return true;
  }
}