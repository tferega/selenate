package net.selenate.common.comms;

import java.util.Optional;
import net.selenate.common.comms.SeAddress;

public final class SeElementSelector implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final Optional<SeAddress>   address;
  private final SeElementSelectMethod method;
  private final String                query;

  public SeElementSelector(
      final Optional<SeAddress>   address,
      final SeElementSelectMethod method,
      final String                query) {
    this.address = address;
    this.method  = method;
    this.query   = query;
    validate();
  }

  public Optional<SeAddress> getAddress() {
    return address;
  }

  public SeElementSelectMethod getMethod() {
    return method;
  }

  public String getQuery() {
    return query;
  }

  public SeElementSelector withAddress(final Optional<SeAddress> newAddress) {
    return new SeElementSelector(newAddress, this.method, this.query);
  }

  public SeElementSelector withMethod(final SeElementSelectMethod newMethod) {
    return new SeElementSelector(this.address, newMethod, this.query);
  }

  public SeElementSelector withQuery(final String newQuery) {
    return new SeElementSelector(this.address, this.method, newQuery);
  }

  private void validate() {
    if (address == null) {
      throw new IllegalArgumentException("Address cannot be null!");
    }

    if (method == null) {
      throw new IllegalArgumentException("Method cannot be null!");
    }

    if (query == null) {
      throw new IllegalArgumentException("Query cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeElementSelector(%s, %s, %s)",
        address, method, query);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
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
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
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
