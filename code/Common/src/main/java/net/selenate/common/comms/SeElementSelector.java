package net.selenate.common.comms;

import net.selenate.common.exceptions.SeNullArgumentException;

public final class SeElementSelector implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final SeElementSelectMethod method;
  private final String                query;

  public SeElementSelector(
      final SeElementSelectMethod method,
      final String                query) {
    this.method  = method;
    this.query   = query;
    validate();
  }

  public SeElementSelectMethod getMethod() {
    return method;
  }

  public String getQuery() {
    return query;
  }

  public SeElementSelector withMethod(final SeElementSelectMethod newMethod) {
    return new SeElementSelector(newMethod, this.query);
  }

  public SeElementSelector withQuery(final String newQuery) {
    return new SeElementSelector(this.method, newQuery);
  }

  private void validate() {
    if (method == null) {
      throw new SeNullArgumentException("Method");
    }

    if (query == null) {
      throw new SeNullArgumentException("Query");
    }
  }

  @Override
  public String toString() {
    return String.format("SeElementSelector(%s, %s)", method, query);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((method == null) ? 0 : method.hashCode());
    result = prime * result + ((query == null) ? 0 : query.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SeElementSelector other = (SeElementSelector) obj;
    if (method != other.method)
      return false;
    if (query == null) {
      if (other.query != null)
        return false;
    } else if (!query.equals(other.query))
      return false;
    return true;
  }
}
