package net.selenate.common.comms.req;

public class SeReqSikuliClick implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final byte[]  image;
  public final Integer timeoutMillis;

  public SeReqSikuliClick(
      final byte[]  image,
      final Integer timeoutMillis) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    if (timeoutMillis < 0) {
      throw new IllegalArgumentException("Timeout cannot be negative!");
    }

    this.image         = image;
    this.timeoutMillis = timeoutMillis;
  }

  @Override
  public String toString() {
    return String.format("SeReqSikuliClick(%d bytes, %d)", image.length, timeoutMillis);
  }
}
