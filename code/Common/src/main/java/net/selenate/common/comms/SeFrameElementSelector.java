package net.selenate.common.comms;

import java.util.List;

import net.selenate.common.util.Util;

public class SeFrameElementSelector implements SeComms {
  private static final long serialVersionUID = 1L;

  public final String                windowHandle;
  public final List<Integer>         framePath;
  public final SeElementSelectMethod method;
  public final String                query;

  public SeFrameElementSelector(
      final String                windowHandle,
      final List<Integer>         framePath,
      final SeElementSelectMethod method,
      final String                query) {
    if (windowHandle == null) {
      throw new IllegalArgumentException("Window handle cannot be null!");
    }
    if (framePath == null) {
      throw new IllegalArgumentException("Frame path cannot be null!");
    }
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }

    this.windowHandle = windowHandle;
    this.framePath    = framePath;
    this.method       = method;
    this.query        = query;
  }

  @Override
  public String toString() {
    return String.format("SeFrameElementSelector([%s] %s: %s, %s)", windowHandle, Util.simpleListToString(framePath), method, query);
  }
}
