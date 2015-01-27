package net.selenate.common.user;

import java.util.List;

import net.selenate.common.comms.abs.AbstractSeFrame;

public class CaptureFrame extends AbstractSeFrame<CaptureFrame>{
  private static final long serialVersionUID = 1L;
  public final int    index;
  public final String name;
  public final String src;

  public CaptureFrame(
      final int    index,
      final String name,
      final String src,
      final String html,
      final List<CaptureFrame> frameList) {
    super(html, frameList);

    this.index     = index;
    this.name      = name;
    this.src       = src;
  }
}
