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
  public final Long                    waitTimeout;
  public final Long                    waitResolution;
  public final Long                    waitDelay;

  public SeReqSessionSetContext(
      final Boolean                 useFrames,
      final List<SeElementSelector> persistentPresentSelectorList,
      final List<SeElementSelector> persistentAbsentSelectorList,
      final Long                    keepaliveDelayMillis,
      final List<SeCommsReq>        keepaliveReqList,
      final Long                    waitTimeout,
      final Long                    waitResolution,
      final Long                    waitDelay) {
    this.useFrames                     = useFrames;
    this.persistentPresentSelectorList = persistentPresentSelectorList;
    this.persistentAbsentSelectorList  = persistentAbsentSelectorList;
    this.keepaliveDelayMillis          = keepaliveDelayMillis;
    this.waitTimeout                   = waitTimeout;
    this.waitResolution                = waitResolution;
    this.waitDelay                     = waitDelay;
    this.keepaliveReqList              = keepaliveReqList;
    validate();
  }

  public static final SeReqSessionSetContext empty = new SeReqSessionSetContext(null, null, null, null, null, null, null, null);

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

  public Long getWaitTimeout() {
    return waitTimeout;
  }

  public Long getWaitResolution() {
    return waitResolution;
  }

  public Long getWaitDelay() {
    return waitDelay;
  }

  public SeReqSessionSetContext withUseFrames(final Boolean newUseFrames) {
    return new SeReqSessionSetContext(newUseFrames, this.persistentPresentSelectorList, this.persistentAbsentSelectorList, this.keepaliveDelayMillis, this.keepaliveReqList, this.waitTimeout, this.waitResolution, this.waitDelay);
  }

  public SeReqSessionSetContext withPersistentPresentSelectorList(final List<SeElementSelector> newPersistentPresentSelectorList) {
    return new SeReqSessionSetContext(this.useFrames, newPersistentPresentSelectorList, this.persistentAbsentSelectorList, this.keepaliveDelayMillis, this.keepaliveReqList, this.waitTimeout, this.waitResolution, this.waitDelay);
  }

  public SeReqSessionSetContext withPersistentAbsentSelectorList(final List<SeElementSelector> newPersistentAbsentSelectorList) {
    return new SeReqSessionSetContext(this.useFrames, this.persistentPresentSelectorList, newPersistentAbsentSelectorList, this.keepaliveDelayMillis, this.keepaliveReqList, this.waitTimeout, this.waitResolution, this.waitDelay);
  }

  public SeReqSessionSetContext withKeepaliveDelayMillis(final Long newKeepaliveDelayMillis) {
    return new SeReqSessionSetContext(this.useFrames, this.persistentPresentSelectorList, this.persistentAbsentSelectorList, newKeepaliveDelayMillis, this.keepaliveReqList, this.waitTimeout, this.waitResolution, this.waitDelay);
  }

  public SeReqSessionSetContext withKeepaliveReqList(final List<SeCommsReq> newKeepaliveReqList) {
    return new SeReqSessionSetContext(this.useFrames, this.persistentPresentSelectorList, this.persistentAbsentSelectorList, this.keepaliveDelayMillis, newKeepaliveReqList, this.waitTimeout, this.waitResolution, this.waitDelay);
  }

  public SeReqSessionSetContext withWaitTimeout(final Long newWaitTimeout) {
    return new SeReqSessionSetContext(this.useFrames, this.persistentPresentSelectorList, this.persistentAbsentSelectorList, this.keepaliveDelayMillis, this.keepaliveReqList, newWaitTimeout, this.waitResolution, this.waitDelay);
  }

  public SeReqSessionSetContext withWaitResolution(final Long newWaitResolution) {
    return new SeReqSessionSetContext(this.useFrames, this.persistentPresentSelectorList, this.persistentAbsentSelectorList, this.keepaliveDelayMillis, this.keepaliveReqList, this.waitTimeout, newWaitResolution, this.waitDelay);
  }

  public SeReqSessionSetContext withWaitDelay(final Long newWaitDelay) {
    return new SeReqSessionSetContext(this.useFrames, this.persistentPresentSelectorList, this.persistentAbsentSelectorList, this.keepaliveDelayMillis, this.keepaliveReqList, this.waitTimeout, this.waitResolution, newWaitDelay);
  }

  private void validate() {
  }

  @Override
  public String toString() {
    return String.format("SeReqSessionSetSettings(%s, %s, %s, %d, %s, %d, %d, %d)",
        useFrames,
        SelenateUtils.listToString(persistentPresentSelectorList),
        SelenateUtils.listToString(persistentAbsentSelectorList),
        keepaliveDelayMillis,
        SelenateUtils.listToString(keepaliveReqList),
        waitTimeout, waitResolution, waitDelay);
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
    result = prime * result + ((waitDelay == null) ? 0 : waitDelay.hashCode());
    result = prime * result
        + ((waitResolution == null) ? 0 : waitResolution.hashCode());
    result = prime * result
        + ((waitTimeout == null) ? 0 : waitTimeout.hashCode());
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
    if (waitDelay == null) {
      if (other.waitDelay != null)
        return false;
    } else if (!waitDelay.equals(other.waitDelay))
      return false;
    if (waitResolution == null) {
      if (other.waitResolution != null)
        return false;
    } else if (!waitResolution.equals(other.waitResolution))
      return false;
    if (waitTimeout == null) {
      if (other.waitTimeout != null)
        return false;
    } else if (!waitTimeout.equals(other.waitTimeout))
      return false;
    return true;
  }
}
