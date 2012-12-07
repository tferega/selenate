package net.selenate.common.comms.req;

import java.io.Serializable;

public class SeReqClearText implements Serializable {
  private static final long serialVersionUID = 1L;

  public final SeReqSelectMethod method;
  public final String            selector;

  public SeReqClearText(
      final SeReqSelectMethod method,
      final String            selector) {
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }

    this.method   = method;
    this.selector = selector;
  }
}
