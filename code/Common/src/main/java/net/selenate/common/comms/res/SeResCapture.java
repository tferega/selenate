package net.selenate.common.comms.res;

import net.selenate.common.SelenateUtils;

import java.util.List;
import java.util.Set;
import net.selenate.common.comms.*;

public class SeResCapture implements SeCommsRes {
  private static final long serialVersionUID = 1L;

  public final String name;
  public final Long   time;
  public final Set<SeCookie>  cookieList;
  public final List<SeWindow> windowList;

  public SeResCapture(
      final String name,
      final Long   time,
      final Set<SeCookie>   cookieList,
      final List<SeWindow>  windowList) {
    this.name       = name;
    this.time       = time;
    this.cookieList = cookieList;
    this.windowList = windowList;
  }

  @Override
  public String toString() {
    return String.format("SeResCapture(%s, %d, %s, %s)",
        name, time, SelenateUtils.setToString(cookieList), SelenateUtils.listToString(windowList));
  }
}
