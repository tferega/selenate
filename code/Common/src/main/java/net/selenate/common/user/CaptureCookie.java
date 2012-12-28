package net.selenate.common.user;

import java.util.Date;

public class CaptureCookie {
  public final String  domain;
  public final Date    expiry;
  public final String  name;
  public final String  path;
  public final String  value;
  public final boolean isSecure;

  public CaptureCookie(
      final String  domain,
      final Date    expiry,
      final String  name,
      final String  path,
      final String  value,
      final boolean isSecure) {
    this.domain   = domain;
    this.expiry   = expiry;
    this.name     = name;
    this.path     = path;
    this.value    = value;
    this.isSecure = isSecure;
  }
}
