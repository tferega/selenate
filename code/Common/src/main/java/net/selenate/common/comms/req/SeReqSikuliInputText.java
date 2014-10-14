package net.selenate.common.comms.req;

public class SeReqSikuliInputText implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final byte[]  image;
  public final String  text;
  public final Integer timeoutMillis;

  public SeReqSikuliInputText(
      final byte[]  image,
      final String  text,
      final Integer timeoutMillis) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null!");
    }

    if (timeoutMillis < 0) {
      throw new IllegalArgumentException("Timeout cannot be negative!");
    }

    this.image         = image;
    this.text          = text;
    this.timeoutMillis = timeoutMillis;
  }

  @Override
  public String toString() {
    return String.format("SeReqSikuliInputText(%d bytes, %s, %d)", image.length, text, timeoutMillis);
  }
}
