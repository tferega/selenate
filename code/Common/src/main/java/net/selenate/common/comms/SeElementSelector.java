package net.selenate.common.comms;

public class SeElementSelector implements SeComms {
  private static final long serialVersionUID = 1L;

  public final SeElementSelectMethod method;
  public final String                query;

  public SeElementSelector(
      final SeElementSelectMethod method,
      final String                query) {
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }

    this.method = method;
    this.query  = query;
  }

  @Override
  public String toString() {
    return String.format("SeElementSelector(%s, %s)", method, query);
  }
}
