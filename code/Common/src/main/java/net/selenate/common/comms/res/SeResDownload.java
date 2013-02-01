package net.selenate.common.comms.res;

public class SeResDownload implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final byte[] body;

  public SeResDownload(final byte[] body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return String.format("SeResDownload(%d bytes)", body.length);
  }
}
