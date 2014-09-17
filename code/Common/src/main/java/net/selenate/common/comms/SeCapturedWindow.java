package net.selenate.common.comms;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.selenate.common.SelenateUtils;
import net.selenate.common.exceptions.SeEmptyArgumentListException;
import net.selenate.common.exceptions.SeNullArgumentException;

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
      final List<SeCapturedFrame> frameList) {
    this.title      = title;
    this.url        = url;
    this.handle     = handle;
    this.posX       = posX;
    this.posY       = posY;
    this.width      = width;
    this.height     = height;
    this.cookieSet  = cookieSet;
    this.html       = html;
    this.screenshot = screenshot;
    this.frameList  = frameList;
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

  public SeCapturedWindow withTitle(String newTitle) {
    return new SeCapturedWindow(newTitle, this.url, this.handle, this.posX, this.posY, this.width, this.height, this.cookieSet, this.html, this.screenshot, this.frameList);
  }

  public SeCapturedWindow withUrl(String newUrl) {
    return new SeCapturedWindow(this.title, newUrl, this.handle, this.posX, this.posY, this.width, this.height, this.cookieSet, this.html, this.screenshot, this.frameList);
  }

  public SeCapturedWindow withHandle(String newHandle) {
    return new SeCapturedWindow(this.title, this.url, newHandle, this.posX, this.posY, this.width, this.height, this.cookieSet, this.html, this.screenshot, this.frameList);
  }

  public SeCapturedWindow withPosX(int newPosX) {
    return new SeCapturedWindow(this.title, this.url, this.handle, newPosX, this.posY, this.width, this.height, this.cookieSet, this.html, this.screenshot, this.frameList);
  }

  public SeCapturedWindow withPosY(int newPosY) {
    return new SeCapturedWindow(this.title, this.url, this.handle, this.posX, newPosY, this.width, this.height, this.cookieSet, this.html, this.screenshot, this.frameList);
  }

  public SeCapturedWindow withWidth(int newWidth) {
    return new SeCapturedWindow(this.title, this.url, this.handle, this.posX, this.posY, newWidth, this.height, this.cookieSet, this.html, this.screenshot, this.frameList);
  }

  public SeCapturedWindow withHeight(int newHeight) {
    return new SeCapturedWindow(this.title, this.url, this.handle, this.posX, this.posY, this.width, newHeight, this.cookieSet, this.html, this.screenshot, this.frameList);
  }

  public SeCapturedWindow withCookieSet(Set<SeCookie> newCookieSet) {
    return new SeCapturedWindow(this.title, this.url, this.handle, this.posX, this.posY, this.width, this.height, newCookieSet, this.html, this.screenshot, this.frameList);
  }

  public SeCapturedWindow withHtml(String newHtml) {
    return new SeCapturedWindow(this.title, this.url, this.handle, this.posX, this.posY, this.width, this.height, this.cookieSet, newHtml, this.screenshot, this.frameList);
  }

  public SeCapturedWindow withScreenshot(byte[] newScreenshot) {
    return new SeCapturedWindow(this.title, this.url, this.handle, this.posX, this.posY, this.width, this.height, this.cookieSet, this.html, newScreenshot, this.frameList);
  }

  public SeCapturedWindow withFrameList(List<SeCapturedFrame> newFrameList) {
    return new SeCapturedWindow(this.title, this.url, this.handle, this.posX, this.posY, this.width, this.height, this.cookieSet, this.html, this.screenshot, newFrameList);
  }

  public void validate() {
    if (title == null) {
      throw new SeNullArgumentException("Title");
    }

    if (url == null) {
      throw new SeNullArgumentException("URL");
    }

    if (handle == null) {
      throw new SeNullArgumentException("Handle");
    }

    if ("".equals(handle)) {
      throw new SeEmptyArgumentListException("Handle");
    }

    if (cookieSet == null) {
      throw new SeNullArgumentException("Cookie set");
    }

    if (html == null) {
      throw new SeNullArgumentException("Html");
    }

    if (screenshot == null) {
      throw new SeNullArgumentException("Screenshot");
    }

    if (frameList == null) {
      throw new SeNullArgumentException("Frame list");
    }
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
