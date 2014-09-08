package net.selenate.common.comms.req;

public final class SeReqSystemClick implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final int x;
  private final int y;

  public SeReqSystemClick(final int x, final int y) {
    this.x = x;
    this.y = y;
    validate();
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public SeReqSystemClick withX(final int newX) {
    return new SeReqSystemClick(newX, this.y);
  }

  public SeReqSystemClick withY(final int newY) {
    return new SeReqSystemClick(this.x, newY);
  }

  private void validate() {
  }

  @Override
  public String toString() {
    return String.format("SeReqSystemClick($d, $d)", x, y);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
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
    SeReqSystemClick other = (SeReqSystemClick) obj;
    if (x != other.x)
      return false;
    if (y != other.y)
      return false;
    return true;
  }
}
