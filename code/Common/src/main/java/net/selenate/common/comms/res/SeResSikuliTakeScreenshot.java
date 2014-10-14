package net.selenate.common.comms.res;

public class SeResSikuliTakeScreenshot implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  private byte[] image;

  public SeResSikuliTakeScreenshot(final byte[] image) {
    if( image == null) {
      throw new IllegalArgumentException("Screenshot cannot be null!");
    }
    this.image = image;
  }

  public byte[] getImage() {
    return image;
  }

  @Override
  public String toString() {
    return String.format("SeResSikuliTakeScreenshot(%s bytes)", image.length);
  }

}
