package net.selenate.common.comms;

import java.util.Date;
import net.selenate.common.exceptions.*;
import net.selenate.common.SelenateUtils;

public final class SeCookie implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final String  name;
  private final String  value;
  private final String  domain;
  private final String  path;
  private final Date    expiry;
  private final boolean isSecure;

  public SeCookie(
      final String name,
      final String value,
      final String domain,
      final String path,
      final Date expiry,
      final boolean isSecure)
  {
    this.name     = SelenateUtils.guardNullOrEmpty(name, "Name");
    this.value    = SelenateUtils.guardNull(value, "Value");
    this.path     = SelenateUtils.guardNull(path, "Path");
    this.domain   = domain;
    this.isSecure = isSecure;
    this.expiry   = expiry;

    final int nameIdx = name.indexOf(';');
    if (nameIdx != -1) {
      throw new SeInvalidArgumentException(String.format("Name cannot contain a semicolon (semicolon found on index %d of string \"%s\")! ", nameIdx, name));
    }

    if (domain != null) {
      final int domainIdx = domain.indexOf(';');
      if (domainIdx != -1) {
        throw new SeInvalidArgumentException(String.format("Domain cannot contain a colon (colon found on index %d of string \"%s\")! ", domainIdx, domain));
      }
    }
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

  @Override
  public String toString() {
    final String formattedExpiry = (expiry == null) ? null : SelenateUtils.ISO_8601_FORMAT.format(expiry);
    return String.format("SeCookie(%s, %s, %s, %s, %s, %s)",
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