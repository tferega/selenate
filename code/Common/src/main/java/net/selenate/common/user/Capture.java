package net.selenate.common.user;

import java.util.List;
import java.util.Set;

public class Capture {
  public final String name;
  public final Long   time;
  public final Set<CaptureCookie>  cookieList;
  public final List<CaptureWindow> windowList;

  public Capture(
      final String name,
      final Long   time,
      final Set<CaptureCookie>   cookieList,
      final List<CaptureWindow>  windowList) {
    this.name       = name;
    this.time       = time;
    this.cookieList = cookieList;
    this.windowList = windowList;
  }
}
