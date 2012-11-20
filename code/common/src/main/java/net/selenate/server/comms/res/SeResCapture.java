package net.selenate.server.comms.res;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class SeResCapture implements Serializable {
  private static final long serialVersionUID = 1L;

  public final String       name;
  public final Long         time;
  public final SeResElement dom;
  public final Set<SeResCookie>  cookieList;
  public final List<SeResWindow> windowList;

  public SeResCapture(
      final String       name,
      final Long         time,
      final SeResElement dom,
      final Set<SeResCookie>   cookieList,
      final List<SeResWindow>  windowList) {
    this.name       = name;
    this.time       = time;
    this.dom        = dom;
    this.cookieList = cookieList;
    this.windowList = windowList;
  }
}
