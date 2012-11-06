package net.selenate.server.comms.res;

import java.io.Serializable;

public class SeResCapture implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String html;
  public final byte[] screenshot;

  public SeResCapture(
      final String html,
      final byte[] screenshot) {
    if (html == null) {
      throw new IllegalArgumentException("html cannot be null!");
    }

    this.html       = html;
    this.screenshot = screenshot;
  }
}
