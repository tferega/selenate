package net.selenate.common.comms;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SeCookie implements SeComms {
  private static final long serialVersionUID = -8895605477477397939L;
  private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HHmmss");

  private String  domain;
  private Date    expiry;
  private String  name;
  private String  path;
  private String  value;
  private boolean isSecure;

  public SeCookie(String name, String value, String path, Date expiry) {
    this(name, value, null, path, expiry);
  }

  public SeCookie(String name, String value, String domain, String path, Date expiry) {
    this(name, value, domain, path, expiry, false);
  }

  public SeCookie(String name, String value, String domain, String path, Date expiry, boolean isSecure) {
    this.name     = name;
    this.value    = value;
    this.path     = path == null || "".equals(path) ? "/" : path;
    this.domain   = stripPort(domain);
    this.isSecure = isSecure;
    if (expiry != null) {
      // Expiration date is specified in seconds since (UTC) epoch time, so truncate the date.
      this.expiry = new Date(expiry.getTime() / 1000 * 1000);
    } else {
      this.expiry = null;
    }
    validate();
  }

  public SeCookie(String name, String value) {
    this(name, value, "/", null);
  }

  public SeCookie(String name, String value, String path) {
    this(name, value, path, null);
  }

  public String getDomain() {
    return domain;
  }

  public Date getExpiry() {
    return expiry;
  }

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }

  public String getValue() {
    return value;
  }

  public boolean isSecure() {
    return isSecure;
  }

  public SeCookie setDomain(String domain) {
    this.domain = domain;
    return this;
  }

  public SeCookie setExpiry(Date expiry) {
    this.expiry = expiry;
    return this;
  }

  public SeCookie setName(String name) {
    this.name = name;
    return this;
  }

  public SeCookie setPath(String path) {
    this.path = path;
    return this;
  }

  public SeCookie setValue(String value) {
    this.value = value;
    return this;
  }

  public SeCookie setSecure(boolean isSecure) {
    this.isSecure = isSecure;
    return this;
  }

  private static String stripPort(String domain) {
    return (domain == null) ? null : domain.split(":")[0];
  }

  protected void  validate() {
    if (name == null || "".equals(name) || value == null || path == null) {
      throw new IllegalArgumentException("Required attributes are not set or " +
          "any non-null attribute set to null");
    }
    if (name.indexOf(';') != -1) {
      throw new IllegalArgumentException(
          "Cookie names cannot contain a ';': " + name);
    }
    if (domain != null && domain.contains(":")) {
      throw new IllegalArgumentException("Domain should not contain a port: " + domain);
    }
  }

  public String toString() {
    final String formattedExpiry = (expiry == null) ? null : dateFormatter.format(expiry);
    return String.format("Cookie(%s, %s, %s, %s, %s, %s)",
        domain, formattedExpiry, name, path, value, String.valueOf(isSecure));
  }
}
