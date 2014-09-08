package net.selenate.common.comms.req;

import java.util.List;
import net.selenate.common.SelenateUtils;

public final class SeReqWindowFrameSwitch implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  public final List<Integer> framePath;

  public SeReqWindowFrameSwitch(final List<Integer> framePath) {
    this.framePath = framePath;
    validate();
  }

  public List<Integer> getFramePath() {
    return framePath;
  }

  public SeReqWindowFrameSwitch withFramePath(final List<Integer> newFramePath) {
    return new SeReqWindowFrameSwitch(newFramePath);
  }

  private void validate() {
    if (framePath == null) {
      throw new IllegalArgumentException("Frame path cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeReqWindowSwitchFrame(%d, %s)",
        SelenateUtils.listToString(framePath));
  }
}
