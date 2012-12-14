package net.selenate.common.comms;

import java.io.Serializable;
import java.util.List;

public class SeWindow implements Serializable {
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
  public final List<SeFrame> frameList;

  public SeWindow(
      final String title,
      final String url,
      final String handle,
      final int    posX,
      final int    posY,
      final int    width,
      final int    height,
      final String html,
      final byte[] screenshot,
      final List<SeFrame> frameList) {
    this.title      = title;
    this.url        = url;
    this.handle     = handle;
    this.posX       = posX;
    this.posY       = posY;
    this.width      = width;
    this.height     = height;
    this.html       = html;
    this.screenshot = screenshot;
    this.frameList  = frameList;
  }
}
