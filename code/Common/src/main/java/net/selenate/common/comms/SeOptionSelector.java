package net.selenate.common.comms;

import java.io.Serializable;

public class SeOptionSelector implements Serializable {
  private static final long serialVersionUID = 1L;

  public final SeOptionSelectMethod method;
  public final String               query;

  public SeOptionSelector(
      final SeOptionSelectMethod method,
      final String               query) {
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }

    this.method = method;
    this.query  = query;
  }
}
