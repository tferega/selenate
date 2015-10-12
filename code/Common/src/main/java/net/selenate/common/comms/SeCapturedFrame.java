package net.selenate.common.comms;

import java.util.List;

import net.selenate.common.SelenateUtils;

public final class SeCapturedFrame implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final String         windowHandle;
  private final List<Integer>  path;
  private final int            index;
  private final String         name;
  private final String         src;
  private final String         html;

  public SeCapturedFrame(
      final String        windowHandle,
      final List<Integer> path,
      final int           index,
      final String        name,
      final String        src,
      final String        html)
  {
    this.windowHandle = SelenateUtils.guardNull(windowHandle, "WindowHandle");
    this.path         = SelenateUtils.guardNull(path, "Path");
    this.index        = index;
    this.name         = SelenateUtils.guardNull(name, "Name");
    this.src          = SelenateUtils.guardNull(src, "Src");
    this.html         = SelenateUtils.guardNull(html, "Html");
  }

  public String getWindowHandle() {
    return windowHandle;
  }

  public List<Integer> getPath() {
    return path;
  }

  public int getIndex() {
    return index;
  }

  public String getName() {
    return name;
  }

  public String getSrc() {
    return src;
  }

  public String getHtml() {
    return html;
  }

  @Override
  public String toString() {
    return String.format("SeFrame(%s, %s, %d, %s, %s)",
        windowHandle, SelenateUtils.listToString(path), index, name, src);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((html == null) ? 0 : html.hashCode());
    result = prime * result + index;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((path == null) ? 0 : path.hashCode());
    result = prime * result + ((src == null) ? 0 : src.hashCode());
    result = prime * result
        + ((windowHandle == null) ? 0 : windowHandle.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SeCapturedFrame other = (SeCapturedFrame) obj;
    if (html == null) {
      if (other.html != null)
        return false;
    } else if (!html.equals(other.html))
      return false;
    if (index != other.index)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (path == null) {
      if (other.path != null)
        return false;
    } else if (!path.equals(other.path))
      return false;
    if (src == null) {
      if (other.src != null)
        return false;
    } else if (!src.equals(other.src))
      return false;
    if (windowHandle == null) {
      if (other.windowHandle != null)
        return false;
    } else if (!windowHandle.equals(other.windowHandle))
      return false;
    return true;
  }
}