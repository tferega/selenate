package net.selenate.common.user;

import java.util.List;

import com.instantor.amazon.client.data.uri.S3FileURI;

import net.selenate.common.comms.abs.AbstractSeWindows;

public class CaptureWindows extends AbstractSeWindows<CaptureWindow, CaptureFrame> {
  private static final long serialVersionUID = 1L;

  public CaptureWindows(List<CaptureWindow> windows) {
    super(windows);
  }

  public CaptureWindows(List<CaptureWindow> windows, S3FileURI screenshotURI, S3FileURI htmlURI) {
    super(windows, screenshotURI, htmlURI);
  }
}
