package net.selenate.common.comms.req;

import java.io.Serializable;

public class SeReqAppendText implements Serializable {
  private static final long serialVersionUID = 1L;

  public final SeReqSelectMethod method;
  public final String            selector;
  public final String            text;

  public SeReqAppendText(
      final SeReqSelectMethod method,
      final String            selector,
      final String            text) {
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }
    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null!");
    }

    this.method   = method;
    this.selector = selector;
    this.text     = text;
  }
}
