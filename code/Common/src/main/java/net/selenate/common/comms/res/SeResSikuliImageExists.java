package net.selenate.common.comms.res;

public class SeResSikuliImageExists implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (isImageFound ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) { return true; }
    if (obj == null) { return false; }
    if (!(obj instanceof SeResSikuliImageExists)) { return false; }
    SeResSikuliImageExists other = (SeResSikuliImageExists) obj;
    if (isImageFound != other.isImageFound) { return false; }
    return true;
  }
}