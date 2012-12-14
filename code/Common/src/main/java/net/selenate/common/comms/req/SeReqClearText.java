package net.selenate.common.comms.req;

import java.io.Serializable;

import net.selenate.common.comms.*;

public class SeReqClearText implements Serializable {
  private static final long serialVersionUID = 1L;

  public final SeSelectMethod method;
  public final String            query;

  public SeReqClearText(
      final SeSelectMethod method,
      final String            query) {
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }

    this.method   = method;
    this.query = query;
  }

  public SeReqClearText(final SeElementSelector selector) {
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }

    this.method = selector.method;
    this.query  = selector.query;
  }
}
