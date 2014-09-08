package net.selenate.common.comms;

public final class SeOptionSelector implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final SeOptionSelectMethod method;
  private final String               query;

  public SeOptionSelector(
      final SeOptionSelectMethod method,
      final String                query) {
    this.method = method;
    this.query  = query;
    validate();
  }

  public SeOptionSelectMethod getMethod() {
    return method;
  }

  public String getQuery() {
    return query;
  }

  public SeOptionSelector withMethod(final SeOptionSelectMethod newMethod) {
    return new SeOptionSelector(newMethod, this.query);
  }

  public SeOptionSelector withQuery(final String newQuery) {
    return new SeOptionSelector(this.method, newQuery);
  }

  private void validate() {
    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }

    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeOptionSelector(%s, %s)", method, query);
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
    SeOptionSelector other = (SeOptionSelector) obj;
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
