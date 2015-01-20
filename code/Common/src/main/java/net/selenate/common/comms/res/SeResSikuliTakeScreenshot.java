package net.selenate.common.comms.res;

import java.util.*;

import net.selenate.common.SelenateUtils;

public class SeResSikuliTakeScreenshot implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private byte[] image;

  public SeResSikuliTakeScreenshot(final byte[] image) {
    this.image = SelenateUtils.guardNull(image, "Image");
  }

  public byte[] getImage() {
    return image;
  }

  @Override
  public String toString() {
    return String.format("SeResSikuliTakeScreenshot(%s bytes)", image.length);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(image);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeResSikuliTakeScreenshot other = (SeResSikuliTakeScreenshot) obj;
    if (!Arrays.equals(image, other.image)) return false;
    return true;
  }
}
