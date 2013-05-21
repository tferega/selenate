package net.selenate.common.comms.req;

import java.util.List;

import net.selenate.common.comms.*;
import net.selenate.common.util.Util;

public class SeReqSelectOption implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String                windowHandle;
  public final List<Integer>         framePath;
  public final SeElementSelectMethod parentMethod;
  public final String                parentQuery;
  public final SeOptionSelectMethod  optionMethod;
  public final String                optionQuery;

  public SeReqSelectOption(
      final String                windowHandle,
      final List<Integer>         framePath,
      final SeElementSelectMethod parentMethod,
      final String                parentQuery,
      final SeOptionSelectMethod  optionMethod,
      final String                optionQuery) {
    if (windowHandle == null) {
      throw new IllegalArgumentException("Window handle cannot be null!");
    }
    if (framePath == null) {
      throw new IllegalArgumentException("Frame path cannot be null!");
    }
    if (parentMethod == null) {
      throw new IllegalArgumentException("Parent method cannot be null!");
    }
    if (parentQuery == null) {
      throw new IllegalArgumentException("Parent query cannot be null!");
    }
    if (optionMethod == null) {
      throw new IllegalArgumentException("Option method cannot be null!");
    }
    if (optionQuery == null) {
      throw new IllegalArgumentException("Option query cannot be null!");
    }

    this.windowHandle = windowHandle;
    this.framePath    = framePath;
    this.parentMethod = parentMethod;
    this.parentQuery  = parentQuery;
    this.optionMethod = optionMethod;
    this.optionQuery  = optionQuery;
  }

  public SeReqSelectOption(
      final SeFrameElementSelector parentSelector,
      final SeOptionSelector       optionSelector) {
    if (parentSelector == null) {
      throw new IllegalArgumentException("Parent selector cannot be null!");
    }
    if (optionSelector == null) {
      throw new IllegalArgumentException("Option selector cannot be null!");
    }

    this.windowHandle = parentSelector.windowHandle;
    this.framePath    = parentSelector.framePath;
    this.parentMethod = parentSelector.method;
    this.parentQuery  = parentSelector.query;
    this.optionMethod = optionSelector.method;
    this.optionQuery  = optionSelector.query;
  }

  @Override
  public String toString() {
    return String.format("SeReqSelectOption([%s] %s: %s, %s, %s, %s)", windowHandle, Util.simpleListToString(framePath), parentMethod, parentQuery, optionMethod, optionQuery);
  }
}
