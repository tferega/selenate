package net.selenate.common.comms;

import net.selenate.common.exceptions.SeNullArgumentException;

public final class SeElementSelector implements SeComms {
  private static final long serialVersionUID = 45749879L;

//  private final Optional<String>      uuid = null;
  private final SeElementSelectMethod method;
  private final SeElementVisibility   visibility;
  private final String                query;

  public SeElementSelector(
      //final Optional<String>      uuid,
      final SeElementSelectMethod method,
      final SeElementVisibility   visibility,
      final String                query) {
//    this.uuid    = uuid;
    this.method     = method;
    this.visibility = visibility;
    this.query      = query;
    validate();
  }

//  public Optional<String> getUUID() {
//    return uuid;
//  }

  public SeElementSelectMethod getMethod() {
    return method;
  }

  public SeElementVisibility getVisibility() {
    return visibility;
  }

  public String getQuery() {
    return query;
  }

//  public SeElementSelector withUuid(final Optional<String> newUuid) {
//    return new SeElementSelector(newUuid, this.method, this.query);
//  }

  public SeElementSelector withMethod(final SeElementSelectMethod newMethod) {
    return new SeElementSelector(/*this.uuid, */newMethod, this.visibility, this.query);
  }

  public SeElementSelector withVisibility(final SeElementVisibility newVisibility) {
    return new SeElementSelector(/*this.uuid, */this.method, newVisibility, this.query);
  }

  public SeElementSelector withQuery(final String newQuery) {
    return new SeElementSelector(/*this.uuid, */this.method, this.visibility, newQuery);
  }

  private void validate() {
//    if (uuid == null) {
//      throw new SeNullArgumentException("Uuid");
//    }

    if (method == null) {
      throw new SeNullArgumentException("Method");
    }

    if (visibility == null) {
      throw new SeNullArgumentException("Visibility");
    }

    if (query == null) {
      throw new SeNullArgumentException("Query");
    }
  }

  @Override
  public String toString() {
    return String.format("SeElementSelector(%s, %s, %s)",
        /*SelenateUtils.optionalToString(uuid), */method, visibility, query);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((method == null) ? 0 : method.hashCode());
    result = prime * result + ((query == null) ? 0 : query.hashCode());
//    result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
    result = prime * result
        + ((visibility == null) ? 0 : visibility.hashCode());
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
//    if (uuid == null) {
//      if (other.uuid != null)
//        return false;
//    } else if (!uuid.equals(other.uuid))
//      return false;
    if (visibility != other.visibility)
      return false;
    return true;
  }
}
