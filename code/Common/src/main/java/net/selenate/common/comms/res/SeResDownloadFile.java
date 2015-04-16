package net.selenate.common.comms.res;

public class SeResDownloadFile implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final byte[] body;
  public final String fileExtension;

  public SeResDownloadFile(final byte[] body, final String fileExtension) {
    this.body          = body;
    this.fileExtension = fileExtension;
  }

  @Override
  public String toString() {
    return String.format("SeResDownload(%d bytes, %s file)", body.length, fileExtension);
  }
}
