package net.selenate.common.comms;

import java.util.List;
import net.selenate.common.SelenateUtils;

public final class SeAddress implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final String        windowHandle;
  private final List<Integer> framePath;

  public SeAddress(
      final String        windowHandle,
      final List<Integer> framePath) {
    this.windowHandle = windowHandle;
    this.framePath    = framePath;
    validate();
  }

  public String getWindowHandle() {
    return windowHandle;
  }

  public List<Integer> getFramePath() {
    return framePath;
  }

  public SeAddress withWindowHandle(final String newWindowHandle) {
    return new SeAddress(newWindowHandle, this.framePath);
  }

  public SeAddress withFramePath(final List<Integer> newFramePath) {
    return new SeAddress(this.windowHandle, newFramePath);
  }

  private void validate() {
    if (windowHandle == null) {
      throw new IllegalArgumentException("Window handle cannot be null!");
    }

    if ("".equals(windowHandle)) {
      throw new IllegalArgumentException("Window handle cannot be empty!");
    }

    if (framePath == null) {
      throw new IllegalArgumentException("Frame path handle cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeAddress(%s, %s)",
        windowHandle, SelenateUtils.listToString(framePath));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((framePath == null) ? 0 : framePath.hashCode());
    result = prime * result
        + ((windowHandle == null) ? 0 : windowHandle.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SeAddress other = (SeAddress) obj;
    if (framePath == null) {
      if (other.framePath != null)
        return false;
    } else if (!framePath.equals(other.framePath))
      return false;
    if (windowHandle == null) {
      if (other.windowHandle != null)
        return false;
    } else if (!windowHandle.equals(other.windowHandle))
      return false;
    return true;
  }
}
