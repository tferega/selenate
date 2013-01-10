package net.selenate.common.comms;

import java.util.List;

public class SeFrameElementSelector extends SeComms {
  private static final long serialVersionUID = 1L;

  public final List<Integer>  framePath;
  public final SeElementSelectMethod method;
  public final String         query;

  public SeFrameElementSelector(
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

  @Override
  public String toString() {
    String framePathStr = "[";
    boolean isFirst = true;
    for (int f : framePath) {
      framePathStr += (isFirst ? "" : ":") + f;
      isFirst = false;
    }
    framePathStr += "]";
    return String.format("SeFrameElementSelector(%s: %s, %s)", framePathStr, method, query);
  }
}
