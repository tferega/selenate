package net.selenate.common.comms;

import java.util.List;

import com.instantor.amazon.client.data.uri.S3FileURI;

import net.selenate.common.comms.abs.AbstractSeWindows;

public class SeWindows extends AbstractSeWindows<SeWindow, SeFrame> {
  private static final long serialVersionUID = 1L;

  public SeWindows(List<SeWindow> windows) {
    super(windows);
  }

  public SeWindows(List<SeWindow> windows, S3FileURI screenshotURI, S3FileURI htmlURI) {
    super(windows, screenshotURI, htmlURI);
  }
}
