package net.selenate.common.comms.req;

import java.io.Serializable;
import java.util.List;

import net.selenate.common.comms.*;

public class SeReqAppendText implements Serializable {
  private static final long serialVersionUID = 1L;

  public final List<Integer>  framePath;
  public final SeSelectMethod method;
  public final String         query;
  public final String         text;

  public SeReqAppendText(
      final List<Integer>  framePath,
      final SeSelectMethod method,
      final String         query,
      final String         text) {
    if (framePath == null) {
      throw new IllegalArgumentException("Frame path cannot be null!");
    }
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }
    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null!");
    }

    this.framePath = framePath;
    this.method    = method;
    this.query     = query;
    this.text      = text;
  }

  public SeReqAppendText(
      final SeFrameElementSelector selector,
      final String                 text) {
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }
    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null!");
    }

    this.framePath = selector.framePath;
    this.method    = selector.method;
    this.query     = selector.query;
    this.text      = text;
  }
}
