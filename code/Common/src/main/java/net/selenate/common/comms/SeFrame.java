package net.selenate.common.comms;

import java.util.List;

import net.selenate.common.comms.abs.AbstractSeFrame;

public class SeFrame extends AbstractSeFrame<SeFrame> implements SeComms {
  private static final long serialVersionUID = 1L;

  public final int    index;
  public final String name;
  public final String src;
  public final String windowHandle;

  public SeFrame(
      final int    index,
      final String name,
      final String src,
      final String html,
      final String windowHandle,
      final List<SeFrame> frameList) {
    super(html, frameList);
    this.index        = index;
    this.name         = name;
    this.src          = src;
    this.windowHandle = windowHandle;
  }

  @Override
  public String toString() {
    return String.format("SeFrame(%d, %s)", index, name);
  }
}
