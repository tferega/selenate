package net.selenate.common.comms.res;

import com.instantor.amazon.client.data.uri.*;

public class SeResCaptureWindow implements SeCommsRes{
  private static final long serialVersionUID = 1L;
  public final byte[] screenshot;
  public S3FileURI fileURI;

  public SeResCaptureWindow(final byte[] screenshot){
    this(screenshot, null);
  }

  public SeResCaptureWindow(final byte[] screenshot, S3FileURI fileURI){
    if( screenshot == null) {
      throw new IllegalArgumentException("Screenshot cannot be null!");
    }
    if( fileURI == null) {
      throw new IllegalArgumentException("FileURI cannot be null!");
    }
    this.screenshot = screenshot;
    this.fileURI    = fileURI;
  }

  @Override
  public String toString() {
    return String.format("SeResCaptureWindow[%s bytes]", screenshot.length);
  }
}
