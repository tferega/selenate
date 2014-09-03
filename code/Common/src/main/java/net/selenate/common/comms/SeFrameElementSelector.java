package net.selenate.common.comms;

import net.selenate.common.comms.SeAddress;

public class SeFrameElementSelector implements SeComms {
  private static final long serialVersionUID = 1L;

  public final SeAddress             address;
  public final SeElementSelectMethod method;
  public final String                query;

  public SeFrameElementSelector(
      final SeAddress             address,
      final SeElementSelectMethod method,
      final String                query) {
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

  @Override
  public String toString() {
    return String.format("SeFrameElementSelector(%s, %s, %s)",
        address, method, query);
  }
}
