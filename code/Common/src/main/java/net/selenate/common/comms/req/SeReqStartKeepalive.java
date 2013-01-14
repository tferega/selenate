package net.selenate.common.comms.req;

import java.util.List;

import net.selenate.common.util.Util;

public class SeReqStartKeepalive implements SeCommsReq {
  private static final long serialVersionUID = 1L;

  public final long delayMillis;
  public final List<SeCommsReq> reqList;

  public SeReqStartKeepalive(long delayMillis, final List<SeCommsReq> reqList) {
    if (reqList == null) {
      throw new IllegalArgumentException("Req list cannot be null!");
    }
    if (reqList.isEmpty()) {
      throw new IllegalArgumentException("Req list cannot be empty!");
    }

    this.delayMillis = delayMillis;
    this.reqList     = reqList;
  }

  @Override
  public String toString() {
    return String.format("SeReqStartKeepalive(%d)%s", delayMillis, Util.multilineListToString(reqList));
  }
}
