package net.selenate.common.comms.req;

public class SeReqSikuliInputText implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final byte[]  image;
  public final String  text;

  public SeReqSikuliInputText(
      final byte[]  image,
      final String  text) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null!");
    }

    this.image = image;
    this.text  = text;
  }

  @Override
  public String toString() {
    return String.format("SeReqSikuliInputText(%d bytes, %s)", image.length, text);
  }
}
