package net.selenate.common.comms.res;

public class SeResCaptureWindow implements SeCommsRes{
  private static final long serialVersionUID = 1L;
  public final byte[] screenshot;

  public SeResCaptureWindow(final byte[] screenshot){
    if( screenshot == null) {
      throw new IllegalArgumentException("Screenshot cannot be null!");
    }
    this.screenshot = screenshot;
  }

  @Override
  public String toString() {
    return String.format("SeResCaptureWindow[%s bytes]", screenshot.length);
  }
}
