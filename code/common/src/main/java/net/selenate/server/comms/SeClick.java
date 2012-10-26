package net.selenate.server.comms;

public class SeClick {
  public final String xpath;

  public SeClick(final String xpath) {
    if (xpath == null) {
      throw new IllegalArgumentException("xpath cannot be null!");
    }

    this.xpath = xpath;
  }
}
