package net.selenate.common.comms.res;

import net.selenate.common.comms.*;

import java.util.Set;

public class SeResCapture implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final String name;
  public final Long   time;
  public final Set<SeCookie>  cookieList;
  public final SeWindows windowList;


  public SeResCapture(
      final String name,
      final Long   time,
      final Set<SeCookie>   cookieList,
      final SeWindows  windowList) {
    this.name          = name;
    this.time          = time;
    this.cookieList    = cookieList;
    this.windowList    = windowList;
  }

  @Override
  public String toString() {
    return String.format("SeResCapture(%s, %d)", name, time);
  }
}
