package net.selenate.common.comms.req;

import java.util.List;

import net.selenate.common.comms.SeElementSelectMethod;
import net.selenate.common.comms.SeFrameElementSelector;
import net.selenate.common.util.Util;

public class SeReqCaptureElement implements SeCommsReq {

  private static final long serialVersionUID = 1L;

  public final List<Integer>         framePath;
  public final SeElementSelectMethod method;
  public final String                query;

  public SeReqCaptureElement(
      final List<Integer>         framePath,
      final SeElementSelectMethod method,
      final String                query) {
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }

    this.framePath = framePath;
    this.method    = method;
    this.query     = query;
  }

  public SeReqCaptureElement(final SeFrameElementSelector selector) {
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }

    this.framePath = selector.framePath;
    this.method = selector.method;
    this.query  = selector.query;
  }

  @Override
  public String toString() {
    return String.format("SeReqCaptureElement(%s, %s, %s)", Util.simpleListToString(framePath), method, query);
  }

}
