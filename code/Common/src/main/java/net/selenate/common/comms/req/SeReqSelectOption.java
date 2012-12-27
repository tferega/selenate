package net.selenate.common.comms.req;

import java.io.Serializable;
import java.util.List;

import net.selenate.common.comms.*;

public class SeReqSelectOption implements Serializable {
  private static final long serialVersionUID = 1L;

  public final List<Integer>         framePath;
  public final SeElementSelectMethod parentMethod;
  public final String                parentQuery;
  public final SeOptionSelectMethod  optionMethod;
  public final String                optionQuery;

  public SeReqSelectOption(
      final List<Integer>         framePath,
      final SeElementSelectMethod parentMethod,
      final String                parentQuery,
      final SeOptionSelectMethod  optionMethod,
      final String                optionQuery) {
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

    this.framePath    = parentSelector.framePath;
    this.parentMethod = parentSelector.method;
    this.parentQuery  = parentSelector.query;
    this.optionMethod = optionSelector.method;
    this.optionQuery  = optionSelector.query;
  }
}
