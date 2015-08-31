package net.selenate.common.comms.req;

public class SeReqSikuliTakeScreenshot implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final byte[] image;
  public final int    width;
  public final int    height;

  public SeReqSikuliTakeScreenshot(
      final byte[] image,
      final int    width,
      final int    height) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null!");
    }

    if (width < 0) {
      throw new IllegalArgumentException("Width cannot be negative!");
    }

    if (height < 0) {
      throw new IllegalArgumentException("Height cannot be negative!");
    }

    this.image  = image;
    this.width  = width;
    this.height = height;
  }

  @Override
  public String toString() {
    return String.format("SeReqSikuliTakeScreenshot(%d bytes, %d, %d)", image.length, width, height);
  }

}
