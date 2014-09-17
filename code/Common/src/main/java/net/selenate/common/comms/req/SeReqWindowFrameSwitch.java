package net.selenate.common.comms.req;

import java.util.List;
import net.selenate.common.exceptions.SeNullArgumentException;
import net.selenate.common.SelenateUtils;

public final class SeReqWindowFrameSwitch implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final List<Integer> framePath;

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
      throw new SeNullArgumentException("Frame path");
    }
  }

  @Override
  public String toString() {
    return String.format("SeReqWindowSwitchFrame(%d, %s)",
        SelenateUtils.listToString(framePath));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((framePath == null) ? 0 : framePath.hashCode());
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
    SeReqWindowFrameSwitch other = (SeReqWindowFrameSwitch) obj;
    if (framePath == null) {
      if (other.framePath != null)
        return false;
    } else if (!framePath.equals(other.framePath))
      return false;
    return true;
  }
}
