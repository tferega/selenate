package net.selenate.server.comms.res;

import java.io.Serializable;
import java.util.List;

public class SeResFrame implements Serializable {
  private static final long serialVersionUID = 1L;

  public final int    index;
  public final String name;
  public final String src;
  public final String html;
  public final byte[] screenshot;
  public final SeResElement dom;
  public final List<SeResFrame> frameList;

  public SeResFrame(
      final int    index,
      final String name,
      final String src,
      final String html,
      final byte[] screenshot,
      final SeResElement dom,
      final List<SeResFrame> frameList) {
    this.index      = index;
    this.name       = name;
    this.src        = src;
    this.html       = html;
    this.screenshot = screenshot;
    this.dom        = dom;
    this.frameList  = frameList;
  }
}
