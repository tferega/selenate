package net.selenate.common.comms.req;

import java.util.Arrays;

import net.selenate.common.SelenateUtils;

public class SeReqSikuliTakeScreenshot implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final byte[] image;
  private final int    width;
  private final int    height;

  public SeReqSikuliTakeScreenshot(final byte[] image, final int width, final int height) {
    if (width < 0) throw new IllegalArgumentException("Width cannot be negative!");
    if (height < 0) throw new IllegalArgumentException("Height cannot be negative!");

    this.image  = SelenateUtils.guardNull(image, "Image");
    this.width  = width;
    this.height = height;
  }

  public byte[] getImage() {
    return image;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public String toString() {
    return String.format("SeReqSikuliTakeScreenshot(%d bytes, %d, %d)", image.length, width, height);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + height;
    result = prime * result + Arrays.hashCode(image);
    result = prime * result + width;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeReqSikuliTakeScreenshot other = (SeReqSikuliTakeScreenshot) obj;
    if (height != other.height) return false;
    if (!Arrays.equals(image, other.image)) return false;
    if (width != other.width) return false;
    return true;
  }
}