package net.selenate.common.user;

import java.util.List;

import net.selenate.common.comms.abs.AbstractSeWindow;

public class CaptureWindow extends AbstractSeWindow<CaptureFrame>{
  public final String   title;
  public final String   url;
  public final String   handle;
  public final Position pos;
  public final Location loc;
  private static final long serialVersionUID = 1L;

  public CaptureWindow(
      final String   title,
      final String   url,
      final String   handle,
      final Position pos,
      final Location loc,
      final String   html,
      final byte[]   screenshot,
      final List<CaptureFrame> frameList) {
    super(html, screenshot, frameList);

    this.title      = title;
    this.url        = url;
    this.handle     = handle;
    this.pos        = pos;
    this.loc        = loc;
  }
}
