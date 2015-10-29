package net.selenate.common.comms.req;

import net.selenate.common.comms.SeElementSelector;
import java.util.List;
import net.selenate.common.SelenateUtils;

public final class SeReqSessionSetContext implements SeCommsReq {
  private static final long serialVersionUID = 45749879L;

  public final Boolean                 useFrames;
  public final List<SeElementSelector> persistentPresentSelectorList;
  public final List<SeElementSelector> persistentAbsentSelectorList;
  public final Long                    keepaliveDelayMillis;
  public final List<SeCommsReq>        keepaliveReqList;

  public SeReqSessionSetContext(
      final Boolean                 useFrames,
      final List<SeElementSelector> persistentPresentSelectorList,
      final List<SeElementSelector> persistentAbsentSelectorList,
      final Long                    keepaliveDelayMillis,
      final List<SeCommsReq>        keepaliveReqList) {
    this.useFrames                     = useFrames;
    this.persistentPresentSelectorList = persistentPresentSelectorList;
    this.persistentAbsentSelectorList  = persistentAbsentSelectorList;
    this.keepaliveDelayMillis          = keepaliveDelayMillis;
    this.keepaliveReqList              = keepaliveReqList;
    validate();
  }

  public static final SeReqSessionSetContext empty = new SeReqSessionSetContext(null, null, null, null, null);

  public Boolean isUseFrames() {
    return useFrames;
  }

  public List<SeElementSelector> getPersistentPresentSelectorList() {
    return persistentPresentSelectorList;
  }

  public List<SeElementSelector> getPersistentAbsentSelectorList() {
    return persistentAbsentSelectorList;
  }

  public Long getKeepaliveDelayMillis() {
    return keepaliveDelayMillis;
  }

  public List<SeCommsReq> getKeepaliveReqList() {
    return keepaliveReqList;
  }

  public SeReqSessionSetContext withUseFrames(final Boolean newUseFrames) {
    return new SeReqSessionSetContext(newUseFrames, this.persistentPresentSelectorList, this.persistentAbsentSelectorList, this.keepaliveDelayMillis, this.keepaliveReqList);
  }

  public SeReqSessionSetContext withPersistentPresentSelectorList(final List<SeElementSelector> newPersistentPresentSelectorList) {
    return new SeReqSessionSetContext(this.useFrames, newPersistentPresentSelectorList, this.persistentAbsentSelectorList, this.keepaliveDelayMillis, this.keepaliveReqList);
  }

  public SeReqSessionSetContext withPersistentAbsentSelectorList(final List<SeElementSelector> newPersistentAbsentSelectorList) {
    return new SeReqSessionSetContext(this.useFrames, this.persistentPresentSelectorList, newPersistentAbsentSelectorList, this.keepaliveDelayMillis, this.keepaliveReqList);
  }

  public SeReqSessionSetContext withKeepaliveDelayMillis(final Long newKeepaliveDelayMillis) {
    return new SeReqSessionSetContext(this.useFrames, this.persistentPresentSelectorList, this.persistentAbsentSelectorList, newKeepaliveDelayMillis, this.keepaliveReqList);
  }

  public SeReqSessionSetContext withKeepaliveReqList(final List<SeCommsReq> newKeepaliveReqList) {
    return new SeReqSessionSetContext(this.useFrames, this.persistentPresentSelectorList, this.persistentAbsentSelectorList, this.keepaliveDelayMillis, newKeepaliveReqList);
  }

  private void validate() {
  }

  @Override
  public String toString() {
    return String.format("SeReqSessionSetSettings(%s, %s, %s, %d, %s)",
        useFrames,
        SelenateUtils.listToString(persistentPresentSelectorList),
        SelenateUtils.listToString(persistentAbsentSelectorList),
        keepaliveDelayMillis,
        SelenateUtils.listToString(keepaliveReqList));
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
    result = prime
        * result
        + ((persistentAbsentSelectorList == null) ? 0
            : persistentAbsentSelectorList.hashCode());
    result = prime
        * result
        + ((persistentPresentSelectorList == null) ? 0
            : persistentPresentSelectorList.hashCode());
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
    if (persistentAbsentSelectorList == null) {
      if (other.persistentAbsentSelectorList != null)
        return false;
    } else if (!persistentAbsentSelectorList
        .equals(other.persistentAbsentSelectorList))
      return false;
    if (persistentPresentSelectorList == null) {
      if (other.persistentPresentSelectorList != null)
        return false;
    } else if (!persistentPresentSelectorList
        .equals(other.persistentPresentSelectorList))
      return false;
    if (useFrames == null) {
      if (other.useFrames != null)
        return false;
    } else if (!useFrames.equals(other.useFrames))
      return false;
    return true;
  }
}
