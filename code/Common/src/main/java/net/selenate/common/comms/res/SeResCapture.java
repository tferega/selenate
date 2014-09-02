package net.selenate.common.comms.res;

import java.util.List;
import java.util.Set;
import net.selenate.common.comms.*;
import net.selenate.common.user.Cookie;
import net.selenate.common.util.Util;

public class SeResCapture implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final String name;
  public final Long   time;
  public final Set<Cookie>  cookieList;
  public final List<SeWindow> windowList;

  public SeResCapture(
      final String name,
      final Long   time,
      final Set<Cookie>   cookieList,
      final List<SeWindow>  windowList) {
    this.name       = name;
    this.time       = time;
    this.cookieList = cookieList;
    this.windowList = windowList;
  }

  @Override
  public String toString() {
    return String.format("SeResCapture(%s, %d, %s, %s)",
        name, time, Util.setToString(cookieList), Util.listToString(windowList));
  }
}
