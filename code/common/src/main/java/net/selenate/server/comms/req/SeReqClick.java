package net.selenate.server.comms.req;

import java.io.Serializable;

public class SeReqClick implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String xpath;

  public SeReqClick(final String xpath) {
    if (xpath == null) {
      throw new IllegalArgumentException("xpath cannot be null!");
    }

    this.xpath = xpath;
  }
}
