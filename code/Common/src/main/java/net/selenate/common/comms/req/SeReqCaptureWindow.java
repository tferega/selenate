package net.selenate.common.comms.req;

import net.selenate.common.comms.SeElementSelectMethod;



public class SeReqCaptureWindow implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final SeElementSelectMethod method;
  public final String         query;

  public SeReqCaptureWindow(
      final SeElementSelectMethod method,
      final String         query) {
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }

    this.method    = method;
    this.query     = query;
  }

  @Override
  public String toString() {
    return String.format("SeReqCaptureWindow()", method, query);
  }
}
