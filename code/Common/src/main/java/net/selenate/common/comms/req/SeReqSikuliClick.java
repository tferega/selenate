package net.selenate.common.comms.req;

public class SeReqSikuliClick implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final byte[]  image;

  public SeReqSikuliClick(
      final byte[]  image,
      final Integer timeoutMillis) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    this.image = image;
  }

  @Override
  public String toString() {
    return String.format("SeReqSikuliClick(%d bytes)", image.length);
  }
}
