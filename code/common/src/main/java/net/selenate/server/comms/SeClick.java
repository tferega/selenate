package net.selenate.server.comms;

import java.io.Serializable;

public class SeClick implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String xpath;

  public SeClick(final String xpath) {
    if (xpath == null) {
      throw new IllegalArgumentException("xpath cannot be null!");
    }

    this.xpath = xpath;
  }
}
