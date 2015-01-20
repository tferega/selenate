package net.selenate.common.comms.req;

import net.selenate.common.SelenateUtils;


public final class SeReqWindowClose implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final String windowHandle;

  public SeReqWindowClose(final String windowHandle) {
    this.windowHandle = SelenateUtils.guardNullOrEmpty(windowHandle, "WindowHandle");
  }

  public String getWindowHandle() {
    return windowHandle;
  }

  public SeReqWindowClose withWindowHandle(final String newWindowHandle) {
    return new SeReqWindowClose(newWindowHandle);
  }

  @Override
  public String toString() {
    return String.format("SeReqWindowClose(%s)", windowHandle);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((windowHandle == null) ? 0 : windowHandle.hashCode());
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
    SeReqWindowClose other = (SeReqWindowClose) obj;
    if (windowHandle == null) {
      if (other.windowHandle != null)
        return false;
    } else if (!windowHandle.equals(other.windowHandle))
      return false;
    return true;
  }
}
