package net.selenate.common.comms.req;

public class SeReqConfigureS3Client implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final String realm;
  public final boolean returnScreenshots;

  public SeReqConfigureS3Client(final String realm, final boolean returnScreenshots) {
    if (realm == null) {
      throw new IllegalArgumentException("realm cannot be null!");
    }

    this.realm       = realm;
    this.returnScreenshots = returnScreenshots;
  }

  @Override
  public String toString() {
    return String.format("SeReqSetConfigureS3Client(%s)", realm);
  }
}
