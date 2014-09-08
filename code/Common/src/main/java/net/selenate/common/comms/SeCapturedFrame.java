package net.selenate.common.comms;

import net.selenate.common.SelenateUtils;
import java.util.List;

public final class SeCapturedFrame implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final int            index;
  private final String         name;
  private final String         src;
  private final String         html;
  private final String         windowHandle;
  private final List<Integer>  path;

  public SeCapturedFrame(
      final int           index,
      final String        name,
      final String        src,
      final String        html,
      final String        windowHandle,
      final List<Integer> path) {
    this.index        = index;
    this.name         = name;
    this.src          = src;
    this.html         = html;
    this.windowHandle = windowHandle;
    this.path         = path;
    validate();
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

  public String getWindowHandle() {
    return windowHandle;
  }

  public List<Integer> getPath() {
    return path;
  }

  public SeCapturedFrame withIndex(int newIndex) {
    return new SeCapturedFrame(newIndex, this.name, this.src, this.html, this.windowHandle, this.path);
  }

  public SeCapturedFrame withName(String newName) {
    return new SeCapturedFrame(this.index, newName, this.src, this.html, this.windowHandle, this.path);
  }

  public SeCapturedFrame withSrc(String newSrc) {
    return new SeCapturedFrame(this.index, this.name, newSrc, this.html, this.windowHandle, this.path);
  }

  public SeCapturedFrame withHtml(String newHtml) {
    return new SeCapturedFrame(this.index, this.name, this.src, newHtml, this.windowHandle, this.path);
  }

  public SeCapturedFrame withWindowHandle(String newWindowHandle) {
    return new SeCapturedFrame(this.index, this.name, this.src, this.html, newWindowHandle, this.path);
  }

  public SeCapturedFrame withPath(List<Integer> newPath) {
    return new SeCapturedFrame(this.index, this.name, this.src, this.html, this.windowHandle, newPath);
  }

  private void validate() {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }

    if (src == null) {
      throw new IllegalArgumentException("Src cannot be null!");
    }

    if (html == null) {
      throw new IllegalArgumentException("Html cannot be null!");
    }

    if (windowHandle == null) {
      throw new IllegalArgumentException("Window handle cannot be null!");
    }

    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeFrame(%d, %s, %s, %s, %s)",
        index, name, src, windowHandle, SelenateUtils.listToString(path));
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
