package net.selenate.common.comms.req;

public class SeReqSikuliImageExists implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final byte[]  image;

  public SeReqSikuliImageExists(
      final byte[]  image,
      final Integer timeoutMillis) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    this.image = image;
  }

  @Override
  public String toString() {
    return String.format("SeReqSikuliImageExists(%d bytes)", image.length);
  }
}
