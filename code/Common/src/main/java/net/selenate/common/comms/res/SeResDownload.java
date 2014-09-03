package net.selenate.common.comms.res;

import net.selenate.common.SelenateUtils;

public class SeResDownload implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final byte[] body;

  public SeResDownload(final byte[] body) {
    if (body == null) {
      throw new IllegalArgumentException("Body cannot be null!");
    }

    this.body = body;
  }

  @Override
  public String toString() {
    return String.format("SeResDownload(%s)", SelenateUtils.byteArrToString(body));
  }
}
