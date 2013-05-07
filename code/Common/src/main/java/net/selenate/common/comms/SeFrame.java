package net.selenate.common.comms;

import java.util.List;

public class SeFrame implements SeComms {
  private static final long serialVersionUID = 1L;

  public final int    index;
  public final String name;
  public final String src;
  public final String html;
  public final String windowHandle;
  public final List<SeFrame> frameList;

  public SeFrame(
      final int    index,
      final String name,
      final String src,
      final String html,
      final String windowHandle,
      final List<SeFrame> frameList) {
    this.index        = index;
    this.name         = name;
    this.src          = src;
    this.html         = html;
    this.windowHandle = windowHandle;
    this.frameList    = frameList;
  }

  @Override
  public String toString() {
    return String.format("SeFrame(%d, %s)", index, name);
  }
}
