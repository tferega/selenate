package net.selenate.common.comms;

import java.util.*;

import net.selenate.common.SelenateUtils;

public final class SeCapturedWindow implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final String        title;
  private final String        url;
  private final String        handle;
  private final int           posX;
  private final int           posY;
  private final int           width;
  private final int           height;
  private final Set<SeCookie> cookieSet;
  private final String        html;
  private final byte[]        screenshot;
  private final List<SeCapturedFrame> frameList;

  public SeCapturedWindow(
      final String        title,
      final String        url,
      final String        handle,
      final int           posX,
      final int           posY,
      final int           width,
      final int           height,
      final Set<SeCookie> cookieSet,
      final String        html,
      final byte[]        screenshot,
      final List<SeCapturedFrame> frameList)
  {
    this.title      = SelenateUtils.guardNull(title, "Title");
    this.url        = SelenateUtils.guardNull(url, "URL");
    this.handle     = SelenateUtils.guardNullOrEmpty(handle, "Handle");
    this.posX       = posX;
    this.posY       = posY;
    this.width      = width;
    this.height     = height;
    this.cookieSet  = SelenateUtils.guardNull(cookieSet, "Cookie set");
    this.html       = SelenateUtils.guardNull(html, "Html");
    this.screenshot = SelenateUtils.guardNull(screenshot, "Screenshot");
    this.frameList  = SelenateUtils.guardNull(frameList, "FrameList");
  }

  public String getTitle() {
    return title;
  }

  public String getUrl() {
    return url;
  }

  public String getHandle() {
    return handle;
  }

  public int getPosX() {
    return posX;
  }

  public int getPosY() {
    return posY;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Set<SeCookie> getCookieSet() {
    return cookieSet;
  }

  public String getHtml() {
    return html;
  }

  public byte[] getScreenshot() {
    return screenshot;
  }

  public List<SeCapturedFrame> getFrameList() {
    return frameList;
  }

  @Override
  public String toString() {
    return String.format("SeWindow(%s, %s, %s, %d, %d, %d, %d, %s, %s, %s)",
        title, url, handle, posX, posY, width, height, SelenateUtils.setToString(cookieSet), SelenateUtils.byteArrToString(screenshot), SelenateUtils.listToString(frameList));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cookieSet == null) ? 0 : cookieSet.hashCode());
    result = prime * result + ((frameList == null) ? 0 : frameList.hashCode());
    result = prime * result + ((handle == null) ? 0 : handle.hashCode());
    result = prime * result + height;
    result = prime * result + ((html == null) ? 0 : html.hashCode());
    result = prime * result + posX;
    result = prime * result + posY;
    result = prime * result + Arrays.hashCode(screenshot);
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((url == null) ? 0 : url.hashCode());
    result = prime * result + width;
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
    SeCapturedWindow other = (SeCapturedWindow) obj;
    if (cookieSet == null) {
      if (other.cookieSet != null)
        return false;
    } else if (!cookieSet.equals(other.cookieSet))
      return false;
    if (frameList == null) {
      if (other.frameList != null)
        return false;
    } else if (!frameList.equals(other.frameList))
      return false;
    if (handle == null) {
      if (other.handle != null)
        return false;
    } else if (!handle.equals(other.handle))
      return false;
    if (height != other.height)
      return false;
    if (html == null) {
      if (other.html != null)
        return false;
    } else if (!html.equals(other.html))
      return false;
    if (posX != other.posX)
      return false;
    if (posY != other.posY)
      return false;
    if (!Arrays.equals(screenshot, other.screenshot))
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (url == null) {
      if (other.url != null)
        return false;
    } else if (!url.equals(other.url))
      return false;
    if (width != other.width)
      return false;
    return true;
  }
}