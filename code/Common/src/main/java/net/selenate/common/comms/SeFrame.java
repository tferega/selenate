package net.selenate.common.comms;

import java.io.Serializable;
import java.util.List;

public class SeFrame implements Serializable {
  private static final long serialVersionUID = 1L;

  public final int    index;
  public final String name;
  public final String src;
  public final String html;
  public final byte[] screenshot;
  public final List<SeFrame> frameList;

  public SeFrame(
      final int    index,
      final String name,
      final String src,
      final String html,
      final byte[] screenshot,
      final List<SeFrame> frameList) {
    this.index      = index;
    this.name       = name;
    this.src        = src;
    this.html       = html;
    this.screenshot = screenshot;
    this.frameList  = frameList;
  }
}
