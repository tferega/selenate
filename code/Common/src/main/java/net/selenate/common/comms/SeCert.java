package net.selenate.common.comms;

import net.selenate.common.SelenateUtils;
import net.selenate.common.exceptions.SeNullArgumentException;

import java.util.Arrays;

public class SeCert implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final String alias;
  private final byte[] body;

  public SeCert(
      final String alias,
      final byte[] body) {
    this.alias = alias;
    this.body = body;
    validate();
  }

  public String getAlias() {
    return alias;
  }

  public byte[] getBody() {
    return body;
  }

  public SeCert withAlias(final String newAlias) {
    return new SeCert(newAlias, this.body);
  }

  public SeCert withBody(final byte[] newBody) {
    return new SeCert(this.alias, newBody);
  }

  private void validate() {
    if (alias == null) {
      throw new SeNullArgumentException("Alias");
    }

    if (body == null) {
      throw new SeNullArgumentException("Body");
    }
  }

  @Override
  public String toString() {
    return String.format("SeCert(%s, %s)", alias, SelenateUtils.byteArrToString(body));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SeCert seCert = (SeCert) o;

    if (!alias.equals(seCert.alias)) return false;
    return Arrays.equals(body, seCert.body);

  }

  @Override
  public int hashCode() {
    int result = alias.hashCode();
    result = 31 * result + Arrays.hashCode(body);
    return result;
  }
}
