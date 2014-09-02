package net.selenate.common.comms;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SeCookie implements SeComms {
  private static final long serialVersionUID = 1L;
  private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HHmmss");

  public final String  domain;
  public final Date    expiry;
  public final String  name;
  public final String  path;
  public final String  value;
  public final boolean isSecure;

  public SeCookie(
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

  @Override
  public String toString() {
    final String formattedExpiry = (expiry == null) ? null : dateFormatter.format(expiry);
    return String.format("SeCookie(%s, %s, %s, %s, %s, %s)",
        domain, formattedExpiry, name, path, value, isSecure);
  }
}
