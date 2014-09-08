package net.selenate.common.comms.req;

public final class SeReqWindowCapture implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final String windowHandle;

  public SeReqWindowCapture(final String windowHandle) {
    this.windowHandle = windowHandle;
    validate();
  }

  public String getWindowHandle() {
    return windowHandle;
  }

  public SeReqWindowCapture withWindowHandle(final String newWindowHandle) {
    return new SeReqWindowCapture(newWindowHandle);
  }

  private void validate() {
    if (windowHandle == null) {
      throw new IllegalArgumentException("Window handle cannot be null!");
    }

    if ("".equals(windowHandle)) {
      throw new IllegalArgumentException("Window handle cannot be empty!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeReqWindowCapture(%s)", windowHandle);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((windowHandle == null) ? 0 : windowHandle.hashCode());
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
    SeReqWindowCapture other = (SeReqWindowCapture) obj;
    if (windowHandle == null) {
      if (other.windowHandle != null)
        return false;
    } else if (!windowHandle.equals(other.windowHandle))
      return false;
    return true;
  }
}
