package net.selenate.common.user;

import java.util.List;

public class CaptureFrame {
  public final int    index;
  public final String name;
  public final String src;
  public final String html;
  public final List<CaptureFrame> frameList;

  public CaptureFrame(
      final int    index,
      final String name,
      final String src,
      final String html,
      final List<CaptureFrame> frameList) {
    this.index     = index;
    this.name      = name;
    this.src       = src;
    this.html      = html;
    this.frameList = frameList;
  }
}
