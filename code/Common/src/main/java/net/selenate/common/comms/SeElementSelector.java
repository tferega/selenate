package net.selenate.common.comms;

import java.util.Optional;

import net.selenate.common.SelenateUtils;

public final class SeElementSelector implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final Optional<String>      uuid;
  private final Optional<Integer>     index;

  private final SeElementSelectMethod method;
  private final String                query;


  public SeElementSelector(
    final Optional<String>      uuid,
    final Optional<Integer>     index,
    final SeElementSelectMethod method,
    final String                query)
  {
    this.uuid    = SelenateUtils.guardNull(uuid, "Uuid");
    this.index   = SelenateUtils.guardNull(index, "Index");
    this.method  = SelenateUtils.guardNull(method, "Method");
    this.query   = SelenateUtils.guardNullOrEmpty(query, "Query");
  }

  public Optional<String> getUUID() {
    return uuid;
  }

  public Optional<Integer> getIndex() {
    return index;
  }

  public SeElementSelectMethod getMethod() {
    return method;
  }

  public String getQuery() {
    return query;
  }


  public SeElementSelector withUUID(final Optional<String> newUuid) {
    return new SeElementSelector(newUuid, this.index, this.method, this.query);
  }

  public SeElementSelector withIndex(final Optional<Integer> newIndex) {
    return new SeElementSelector(this.uuid, newIndex, this.method, this.query);
  }


  @Override
  public String toString() {
    return String.format("SeElementSelector(%s, %s, %s, %s)",
      SelenateUtils.optionalToString(uuid), SelenateUtils.optionalToString(index), method, query);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((index == null) ? 0 : index.hashCode());
    result = prime * result + ((method == null) ? 0 : method.hashCode());
    result = prime * result + ((query == null) ? 0 : query.hashCode());
    result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeElementSelector other = (SeElementSelector) obj;
    if (index == null) {
      if (other.index != null) return false;
    } else if (!index.equals(other.index)) return false;
    if (method != other.method) return false;
    if (query == null) {
      if (other.query != null) return false;
    } else if (!query.equals(other.query)) return false;
    if (uuid == null) {
      if (other.uuid != null) return false;
    } else if (!uuid.equals(other.uuid)) return false;
    return true;
  }
}