package net.selenate.common.comms.req;

public class SeReqClickRelativeLocation implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final int x;
  public final int y;

  public SeReqClickRelativeLocation(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return String.format("SeReqClickRelativeLocation($d, $d)", x, y);
  }
}
