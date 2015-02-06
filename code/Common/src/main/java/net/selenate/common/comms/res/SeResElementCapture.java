package net.selenate.common.comms.res;

import java.util.Arrays;
import net.selenate.common.exceptions.SeNullArgumentException;
import net.selenate.common.SelenateUtils;

public final class SeResElementCapture implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final byte[] body;

  public SeResElementCapture(final byte[] body) {
    this.body = body;
    validate();
  }

  public byte[] getBody() {
    return body;
  }

  public SeResElementCapture withBody(final byte[] newBody) {
    return new SeResElementCapture(newBody);
  }

  private void validate() {
    if (body == null) {
      throw new SeNullArgumentException("Body");
    }
  }

  @Override
  public String toString() {
    return String.format("SeResElementCapture(%s)",
        SelenateUtils.byteArrToString(body));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(body);
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
    SeResElementCapture other = (SeResElementCapture) obj;
    if (!Arrays.equals(body, other.body))
      return false;
    return true;
  }
}
