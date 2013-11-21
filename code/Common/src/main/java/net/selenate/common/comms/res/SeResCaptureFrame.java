package net.selenate.common.comms.res;

public class SeResCaptureFrame implements SeCommsRes{
  private static final long serialVersionUID = 1L;
  public final byte[] screenshot;

  public SeResCaptureFrame(final byte[] screenshot){
    this.screenshot = screenshot;
  }

  @Override
  public String toString() {
    return String.format("SeResCaptureFrame(%s)", screenshot);
  }
}
