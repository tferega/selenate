package net.selenate.common.comms.req;

import java.util.Arrays;

import net.selenate.common.SelenateUtils;

public class SeReqSikuliClick implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final byte[] image;

  public SeReqSikuliClick(final byte[]  image) {
    this.image = SelenateUtils.guardNull(image, "Image");
  }

  public byte[] getImage() {
    return image;
  }

  @Override
  public String toString() {
    return String.format("SeReqSikuliClick(%d bytes)", image.length);
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
    SeReqSikuliClick other = (SeReqSikuliClick) obj;
    if (!Arrays.equals(image, other.image)) return false;
    return true;
  }
}