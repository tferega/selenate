package net.selenate.common.comms.req;

public class SeReqSystemClick implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final int x;
  public final int y;

  public SeReqSystemClick(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return String.format("SeReqSystemClick($d, $d)", x, y);
  }
}
