package net.selenate.common.comms.req;

import java.util.List;
import java.util.Optional;
import net.selenate.common.SelenateUtils;

public final class SeReqSessionSetContext implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  public final Optional<Boolean>          useFrames;
  public final Optional<Long>             keepaliveDelayMillis;
  public final Optional<List<SeCommsReq>> keepaliveReqList;

  public SeReqSessionSetContext(
      final Optional<Boolean>          useFrames,
      final Optional<Long>             keepaliveDelayMillis,
      final Optional<List<SeCommsReq>> keepaliveReqList) {
    this.useFrames            = useFrames;
    this.keepaliveDelayMillis = keepaliveDelayMillis;
    this.keepaliveReqList     = keepaliveReqList;
  }

  public Optional<Boolean> isUseFrames() {
    return useFrames;
  }

  public Optional<Long> getKeepaliveDelayMillis() {
    return keepaliveDelayMillis;
  }

  public Optional<List<SeCommsReq>> getKeepaliveReqList() {
    return keepaliveReqList;
  }

  public SeReqSessionSetContext withUseFrames(final Optional<Boolean> newUseFrames) {
    return new SeReqSessionSetContext(newUseFrames, this.keepaliveDelayMillis, this.keepaliveReqList);
  }

  public SeReqSessionSetContext withKeepaliveDelayMillis(final Optional<Long> newKeepaliveDelayMillis) {
    return new SeReqSessionSetContext(this.useFrames, newKeepaliveDelayMillis, this.keepaliveReqList);
  }

  public SeReqSessionSetContext withkeepaliveReqList(final Optional<List<SeCommsReq>> newKeepaliveReqList) {
    return new SeReqSessionSetContext(this.useFrames, this.keepaliveDelayMillis, newKeepaliveReqList);
  }

  @Override
  public String toString() {
    return String.format("SeReqSessionSetSettings(%s, %d, %s)",
        SelenateUtils.optionalToString(useFrames),
        SelenateUtils.optionalToString(keepaliveDelayMillis),
        SelenateUtils.optionalToString(keepaliveReqList.map(SelenateUtils::listToString)));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime
        * result
        + ((keepaliveDelayMillis == null) ? 0 : keepaliveDelayMillis.hashCode());
    result = prime * result
        + ((keepaliveReqList == null) ? 0 : keepaliveReqList.hashCode());
    result = prime * result + ((useFrames == null) ? 0 : useFrames.hashCode());
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
    SeReqSessionSetContext other = (SeReqSessionSetContext) obj;
    if (keepaliveDelayMillis == null) {
      if (other.keepaliveDelayMillis != null)
        return false;
    } else if (!keepaliveDelayMillis.equals(other.keepaliveDelayMillis))
      return false;
    if (keepaliveReqList == null) {
      if (other.keepaliveReqList != null)
        return false;
    } else if (!keepaliveReqList.equals(other.keepaliveReqList))
      return false;
    if (useFrames == null) {
      if (other.useFrames != null)
        return false;
    } else if (!useFrames.equals(other.useFrames))
      return false;
    return true;
  }
}
