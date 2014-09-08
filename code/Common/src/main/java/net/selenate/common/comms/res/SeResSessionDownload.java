package net.selenate.common.comms.res;

import java.util.Arrays;

import net.selenate.common.SelenateUtils;

public final class SeResSessionDownload implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final byte[] body;

  public SeResSessionDownload(final byte[] body) {
    this.body = body;
    validate();
  }

  public byte[] getBody() {
    return body;
  }

  public SeResSessionDownload withBody(final byte[] newBody) {
    return new SeResSessionDownload(newBody);
  }

  private void validate() {
    if (body == null) {
      throw new IllegalArgumentException("Body cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeResSessionDownload(%s)",
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
    SeResSessionDownload other = (SeResSessionDownload) obj;
    if (!Arrays.equals(body, other.body))
      return false;
    return true;
  }
}
