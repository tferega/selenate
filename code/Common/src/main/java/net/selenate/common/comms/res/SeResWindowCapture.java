package net.selenate.common.comms.res;

import java.util.Arrays;

import net.selenate.common.SelenateUtils;

public final class SeResWindowCapture implements SeCommsRes{
  private static final long serialVersionUID = 45749879L;

  private final byte[] screenshot;

  public SeResWindowCapture(final byte[] screenshot){
    this.screenshot = screenshot;
    validate();
  }

  public byte[] getScreenshot() {
    return screenshot;
  }

  public SeResWindowCapture withScreenshot(final byte[] newScreenshot) {
    return new SeResWindowCapture(newScreenshot);
  }

  private void validate() {
    if (screenshot == null) {
      throw new IllegalArgumentException("Screenshot cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeResWindowCapture(%s)",
        SelenateUtils.byteArrToString(screenshot));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(screenshot);
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
    SeResWindowCapture other = (SeResWindowCapture) obj;
    if (!Arrays.equals(screenshot, other.screenshot))
      return false;
    return true;
  }
}
