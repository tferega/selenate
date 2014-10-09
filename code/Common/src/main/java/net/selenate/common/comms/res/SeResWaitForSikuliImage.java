package net.selenate.common.comms.res;

public class SeResWaitForSikuliImage implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  private boolean isImageFound;

  public SeResWaitForSikuliImage(final boolean isImageFound) {
    this.isImageFound = isImageFound;
  }

  public boolean isImageFound() {
    return isImageFound;
  }

  @Override
  public String toString() {
    return String.format("SeResWaitForSikuliImage(%s)", isImageFound);
  }
}
