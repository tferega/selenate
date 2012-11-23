package net.selenate.server.comms.res;

import java.io.Serializable;
import java.util.List;

public class SeResWindow implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String title;
  public final String url;
  public final String handle;
  public final int    posX;
  public final int    posY;
  public final int    width;
  public final int    height;
  public final String html;
  public final byte[] screenshot;
  public final SeResElement dom;
  public final List<SeResFrame> frameList;

  public SeResWindow(
      final String title,
      final String url,
      final String handle,
      final int    posX,
      final int    posY,
      final int    width,
      final int    height,
      final String html,
      final byte[] screenshot,
      final SeResElement dom,
      final List<SeResFrame> frameList) {
    this.title      = title;
    this.url        = url;
    this.handle     = handle;
    this.posX       = posX;
    this.posY       = posY;
    this.width      = width;
    this.height     = height;
    this.html       = html;
    this.screenshot = screenshot;
    this.dom        = dom;
    this.frameList  = frameList;
  }
}
