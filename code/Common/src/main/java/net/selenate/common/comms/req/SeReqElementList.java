package net.selenate.common.comms.req;

import java.io.Serializable;

public class SeReqElementList implements Serializable {
  private static final long serialVersionUID = 1L;

  public final SeReqSelectMethod method;
  public final String            query;

  public SeReqElementList(
      final SeReqSelectMethod method,
      final String            query) {
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
