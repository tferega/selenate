package net.selenate.common.comms.res;

import net.selenate.common.util.Util;

public class SeResCaptureElement implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final byte[] body;

  public SeResCaptureElement(final byte[] body) {
    if (body == null) {
      throw new IllegalArgumentException("Body cannot be null!");
    }

    this.body = body;
  }

  @Override
  public String toString() {
    return String.format("SeResCaptureElement(%s)", Util.byteArrToString(body));
  }
}
