package net.selenate.server.comms;

public class SeGet {
  public final String url;

  public SeGet(final String url) {
    if (url == null) {
      throw new IllegalArgumentException("url cannot be null!");
    }

    this.url = url;
  }
}
