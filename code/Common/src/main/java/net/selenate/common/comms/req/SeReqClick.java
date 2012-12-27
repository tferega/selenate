package net.selenate.common.comms.req;

import java.io.Serializable;
import java.util.List;

import net.selenate.common.comms.*;

public class SeReqClick implements Serializable {
  private static final long serialVersionUID = 1L;

  public final List<Integer>  framePath;
  public final SeElementSelectMethod method;
  public final String         query;

  public SeReqClick(
      final List<Integer>  framePath,
      final SeElementSelectMethod method,
      final String         query) {
    if (framePath == null) {
      throw new IllegalArgumentException("Frame path cannot be null!");
    }
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

  public SeReqClick(final SeFrameElementSelector selector) {
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }

    this.framePath = selector.framePath;
    this.method    = selector.method;
    this.query     = selector.query;
  }
}
