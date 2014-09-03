package net.selenate.common.comms;

import java.util.Date;
import net.selenate.common.SelenateUtils;

public final class SeCookie implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final String  name;
  private final String  value;
  private final String  domain;
  private final String  path;
  private final Date    expiry;
  private final boolean isSecure;

  private static String stripPort(final String domain) {
    return (domain == null) ? null : domain.split(":")[0];
  }

  public SeCookie(
      final String name,
      final String value) {
    this(name, value, null, "/", null, false);
  }

  public SeCookie(
      final String name,
      final String value,
      final String path) {
    this(name, value, null, path, null, false);
  }

  public SeCookie(
      final String name,
      final String value,
      final String path,
      final Date expiry) {
    this(name, value, null, path, expiry, false);
  }

  public SeCookie(
      final String name,
      final String value,
      final String domain,
      final String path,
      final Date expiry) {
    this(name, value, domain, path, expiry, false);
  }

  public SeCookie(
      final String name,
      final String value,
      final String domain,
      final String path,
      final Date expiry,
      final boolean isSecure) {
    this.name     = name;
    this.value    = value;
    this.path     = (path == null || "".equals(path)) ? "/" : path;
    this.domain   = stripPort(domain);
    this.isSecure = isSecure;
    this.expiry   = expiry;
    validate();
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public String getDomain() {
    return domain;
  }

  public String getPath() {
    return path;
  }

  public Date getExpiry() {
    return expiry;
  }

  public boolean isSecure() {
    return isSecure;
  }

  public SeCookie withName(final String newName) {
    return new SeCookie(newName, this.value, this.domain, this.path, this.expiry, this.isSecure);
  }

  public SeCookie withValue(final String newValue) {
    return new SeCookie(this.name, newValue, this.domain, this.path, this.expiry, this.isSecure);
  }

  public SeCookie withDomain(final String newDomain) {
    return new SeCookie(this.name, this.value, newDomain, this.path, this.expiry, this.isSecure);
  }

  public SeCookie withPath(final String newPath) {
    return new SeCookie(this.name, this.value, this.domain, newPath, this.expiry, this.isSecure);
  }

  public SeCookie withExpiry(final Date newExpiry) {
    return new SeCookie(this.name, this.value, this.domain, this.path, newExpiry, this.isSecure);
  }

  public SeCookie withSecure(final boolean newIsSecure) {
    return new SeCookie(this.name, this.value, this.domain, this.path, this.expiry, newIsSecure);
  }

  private void  validate() {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }

    if ("".equals(name)) {
      throw new IllegalArgumentException("Name cannot be empty!");
    }

    if (value == null) {
      throw new IllegalArgumentException("Value cannot be null!");
    }

    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null!");
    }

    final int nameIdx = name.indexOf(';');
    if (nameIdx != -1) {
      throw new IllegalArgumentException(String.format("Name cannot contain a semicolon (semicolon found on index %d of string \"%s\")! ", nameIdx, name));
    }

    if (domain != null) {
      final int domainIdx = domain.indexOf(';');
      if (domainIdx != -1) {
        throw new IllegalArgumentException(String.format("Domain cannot contain a colon (colon found on index %d of string \"%s\")! ", domainIdx, domain));
      }
    }
  }

  @Override
  public String toString() {
    final String formattedExpiry = (expiry == null) ? null : SelenateUtils.ISO_8601_FORMAT.format(expiry);
    return String.format("Cookie(%s, %s, %s, %s, %s, %s)",
        domain, formattedExpiry, name, path, value, isSecure);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((domain == null) ? 0 : domain.hashCode());
    result = prime * result + ((expiry == null) ? 0 : expiry.hashCode());
    result = prime * result + (isSecure ? 1231 : 1237);
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((path == null) ? 0 : path.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SeCookie other = (SeCookie) obj;
    if (domain == null) {
      if (other.domain != null)
        return false;
    } else if (!domain.equals(other.domain))
      return false;
    if (expiry == null) {
      if (other.expiry != null)
        return false;
    } else if (!expiry.equals(other.expiry))
      return false;
    if (isSecure != other.isSecure)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (path == null) {
      if (other.path != null)
        return false;
    } else if (!path.equals(other.path))
      return false;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }
}
