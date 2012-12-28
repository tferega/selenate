package net.selenate.common.user;

import java.util.List;

public class CaptureWindow {
  public final String   title;
  public final String   url;
  public final String   handle;
  public final Position pos;
  public final Location loc;
  public final String   html;
  public final byte[]   screenshot;
  public final List<CaptureFrame> frameList;

  public CaptureWindow(
      final String   title,
      final String   url,
      final String   handle,
      final Position pos,
      final Location loc,
      final String   html,
      final byte[]   screenshot,
      final List<CaptureFrame> frameList) {
    this.title      = title;
    this.url        = url;
    this.handle     = handle;
    this.pos        = pos;
    this.loc        = loc;
    this.html       = html;
    this.screenshot = screenshot;
    this.frameList  = frameList;
  }
}
