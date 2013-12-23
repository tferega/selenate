package net.selenate.common.user;

import java.io.Serializable;

public class ElementSelector implements Serializable {
  private static final long serialVersionUID = -3645313388839524989L;

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
