package net.selenate.common.comms.res;

public class SeResSikuliImageExists implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  private boolean isImageFound;

  public SeResSikuliImageExists(final boolean isImageFound) {
    this.isImageFound = isImageFound;
  }

  public boolean isImageFound() {
    return isImageFound;
  }

  @Override
  public String toString() {
    return String.format("SeResSikuliImageExists(%s)", isImageFound);
  }
}
