package net.selenate.common.comms.req;

import net.selenate.common.comms.SeAddress;
import net.selenate.common.comms.SeElementSelectMethod;
import net.selenate.common.comms.SeFrameElementSelector;

public class SeReqClearText implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final SeAddress             address;
  public final SeElementSelectMethod method;
  public final String                query;

  public SeReqClearText(
      final SeAddress             address,
      final SeElementSelectMethod method,
      final String         query) {
    if (address == null) {
      throw new IllegalArgumentException("Address cannot be null!");
    }
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }

    this.address = address;
    this.method  = method;
    this.query   = query;
  }

  public SeReqClearText(final SeFrameElementSelector selector) {
    if (selector == null) {
      throw new IllegalArgumentException("Selector cannot be null!");
    }

    this.address = selector.address;
    this.method  = selector.method;
    this.query   = selector.query;
  }

  @Override
  public String toString() {
    return String.format("SeReqClearText(%s, %s, %s)",
        address, method, query);
  }
}
