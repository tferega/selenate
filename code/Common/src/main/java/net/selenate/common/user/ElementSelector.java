package net.selenate.common.user;

public class ElementSelector {
  public final ElementSelectMethod method;
  public final String              query;

  public ElementSelector(
      final ElementSelectMethod method,
      final String              query) {
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }
    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }

    this.method   = method;
    this.query = query;
  }

  @Override
  public String toString() {
    return String.format("ElementSelectMethod[%s, %s]", method, query);
  }
}
