package net.selenate.common.comms.res;

public class SeResSikuliImageExists implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  private boolean imageExists;

  public SeResSikuliImageExists(final boolean imageExists) {
    this.imageExists = imageExists;
  }

  @Override
  public String toString() {
    return String.format("SeResSikuliImageExists(%s)", imageExists);
  }
}
