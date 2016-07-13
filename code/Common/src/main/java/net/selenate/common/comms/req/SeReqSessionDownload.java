package net.selenate.common.comms.req;

import java.net.URL;
import java.util.List;

import net.selenate.common.SelenateUtils;
import net.selenate.common.exceptions.SeInvalidArgumentException;
import net.selenate.common.exceptions.SeNullArgumentException;

public final class SeReqSessionDownload implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  private final String url;
  private final List<byte[]> certList;

  public SeReqSessionDownload(
      final String url,
      final List<byte[]> certList) {
    this.url = url;
    this.certList = certList;
    validate();
  }

  public String getUrl() {
    return url;
  }

  public List<byte[]> getCertList() {
    return certList;
  }

  public SeReqSessionDownload withUrl(final String newUrl) {
    return new SeReqSessionDownload(newUrl, this.certList);
  }

  public SeReqSessionDownload withCertList(final List<byte[]> newCertList) {
    return new SeReqSessionDownload(this.url, newCertList);
  }

  private void validate() {
    if (url == null) {
      throw new SeNullArgumentException("Url");
    }

    try {
      new URL(url);
    } catch (final Exception e) {
      throw new SeInvalidArgumentException("An error occurred while interpreting url as java.net.URL!", e);
    }

    if (certList == null) {
      throw new SeNullArgumentException("CertList");
    }
  }

  @Override
  public String toString() {
    return String.format("SeReqSessionDownload(%s, %s)", url, SelenateUtils.listToString(certList));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SeReqSessionDownload that = (SeReqSessionDownload) o;

    if (!url.equals(that.url)) return false;
    return certList.equals(that.certList);

  }

  @Override
  public int hashCode() {
    int result = url.hashCode();
    result = 31 * result + certList.hashCode();
    return result;
  }
}
