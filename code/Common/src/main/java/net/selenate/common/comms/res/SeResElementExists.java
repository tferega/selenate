package net.selenate.common.comms.res;

public final class SeResElementExists implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final boolean isFound;

  public SeResElementExists(final boolean isFound) {
    this.isFound = isFound;
  }

  public boolean IsFound() {
    return isFound;
  }

  public SeResElementExists withIsFound(final boolean newIsFound) {
    return new SeResElementExists(newIsFound);
  }

  @Override
  public String toString() {
    return String.format("SeResElementExists(%s)", isFound);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (isFound ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SeResElementExists other = (SeResElementExists) obj;
    if (isFound != other.isFound)
      return false;
    return true;
  }
}
