package net.selenate.common.comms.res;

public class SeResCaptureElement implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final byte[] body;

  public SeResCaptureElement(final byte[] body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return String.format("SeResCaptureElement(%d bytes)", body.length);
  }
}
