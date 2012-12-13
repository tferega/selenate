package net.selenate.common.comms.req;

import java.io.Serializable;

public class SeReqAppendText implements Serializable {
  private static final long serialVersionUID = 1L;

  public final SeReqSelectMethod method;
  public final String            query;
  public final String            text;

  public SeReqAppendText(
      final SeReqSelectMethod method,
      final String            query,
      final String            text) {
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }
    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null!");
    }

    this.method = method;
    this.query  = query;
    this.text   = text;
  }

  public SeReqAppendText(
      final SeReqElementSelector selector,
      final String               text) {
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }
    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null!");
    }

    this.method = selector.method;
    this.query  = selector.query;
    this.text   = text;
  }
}
