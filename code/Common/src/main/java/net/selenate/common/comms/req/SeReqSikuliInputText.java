package net.selenate.common.comms.req;

import java.util.Arrays;

import net.selenate.common.SelenateUtils;

public class SeReqSikuliInputText implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final byte[]  image;
  private final String  text;

  public SeReqSikuliInputText(final byte[]  image, final String  text) {
    this.image = SelenateUtils.guardNull(image, "Image");
    this.text  = SelenateUtils.guardNull(text, "Text");
  }

  public byte[] getImage() {
    return image;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return String.format("SeReqSikuliInputText(%d bytes, %s)", image.length, text);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(image);
    result = prime * result + ((text == null) ? 0 : text.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeReqSikuliInputText other = (SeReqSikuliInputText) obj;
    if (!Arrays.equals(image, other.image)) return false;
    if (text == null) {
      if (other.text != null) return false;
    } else if (!text.equals(other.text)) return false;
    return true;
  }
}