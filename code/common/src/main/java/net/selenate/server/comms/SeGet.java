package net.selenate.server.comms;

import java.io.Serializable;

public class SeGet implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String url;

  public SeGet(final String url) {
    if (url == null) {
      throw new IllegalArgumentException("url cannot be null!");
    }

    this.url = url;
  }
}
