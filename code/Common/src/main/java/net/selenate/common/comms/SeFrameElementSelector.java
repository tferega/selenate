package net.selenate.common.comms;

import java.io.Serializable;
import java.util.List;

public class SeFrameElementSelector implements Serializable {
  private static final long serialVersionUID = 1L;

  public final List<Integer>  framePath;
  public final SeSelectMethod method;
  public final String         query;

  public SeFrameElementSelector(
      final List<Integer>  framePath,
      final SeSelectMethod method,
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
}
