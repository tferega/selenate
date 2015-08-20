package net.selenate.client.user.test;

import java.util.Date;
import java.util.List;
import java.util.Set;

import net.selenate.common.comms.*;
import net.selenate.common.comms.res.*;
import java.io.*;
import java.util.Iterator;

public class SaveCapture {
  public static final String captureFolder = "/home/huitz/work/selenate/";

  public static final String invalidCharsRegex = "[^-\\w\\.\\s]";
  public static final String invalidCharsReplacement = "_";
  public static String sanitize(final String filename) {
    return filename.replaceAll(invalidCharsRegex, invalidCharsReplacement);
  }

  public static void saveCapture(final SeResCapture capture) {
    final String baseFolder   = captureFolder + String.format("%d - %s/", capture.time, capture.name);
    final String cookieFolder = baseFolder + "cookies/";
    final String windowFolder = baseFolder + "windows/";

    final String filename = baseFolder + "capture.txt";
    final String body =
        "NAME:         " + capture.name + "\n" +
        "TIME:         " + String.format("%s (%d)", new Date(capture.time).toString(), capture.time) + "\n" +
        "COOKIE COUNT: " + capture.cookieList.size() + "\n" +
        "WINDOW COUNT: " + capture.windowList.size() + "\n";
    saveFile(filename, body);

    saveCookieList(cookieFolder, capture.cookieList);
    saveWindowList(windowFolder, capture.windowList);
  }

  public static void saveCookieList(final String folder, final Set<SeCookie> cookieList) {
    int n = 0;
    Iterator<SeCookie> iter = cookieList.iterator();
    while (iter.hasNext()) {
      n++;
      final SeCookie cookie = iter.next();
      final String filename = folder + String.format("%d - %s.txt", n, sanitize(cookie.name));
      final String body =
          "DOMAIN:    " + cookie.domain + "\n" +
          "EXPIRY:    " + cookie.expiry + "\n" +
          "NAME:      " + cookie.name   + "\n" +
          "PATH:      " + cookie.path   + "\n" +
          "VALUE:     " + cookie.value  + "\n" +
          "IS SECURE: " + cookie.isSecure;
      saveFile(filename, body);
    }
  }

  public static void saveWindowList(final String baseFolder, final List<SeWindow> windowList) {
    for (int n = 0; n < windowList.size(); n++) {
      final SeWindow window = windowList.get(n);
      final String folder = baseFolder + String.format("%d - %s/", n+1, sanitize(window.title));

      final String filename = folder + "window.txt";
      final String body =
          "TITLE:       " + window.title  + "\n" +
          "URL:         " + window.url    + "\n" +
          "HANDLE:      " + window.handle + "\n" +
          "POS X:       " + window.posX   + "\n" +
          "POS Y:       " + window.posY   + "\n" +
          "WIDTH:       " + window.width  + "\n" +
          "HEIGHT:      " + window.height + "\n" +
          "FRAME COUNT: " + window.frameList.size();
      saveFile(filename, body);

      final String htmlFilename = folder + "body.html";
      saveFile(htmlFilename, window.html);

      final String screenshotFilename = folder + "screenshot.png";
      saveFile(screenshotFilename, window.screenshot);

      final String frameFolder = folder + "frames/";
      saveFrameList(frameFolder, window.frameList);
    }
  }

  public static void saveFrameList(final String baseFolder, final List<SeFrame> frameList) {
    for (int n = 0; n < frameList.size(); n++) {
      final SeFrame frame = frameList.get(n);
      final String folder = baseFolder + String.format("%s/", n+1);

      final String filename = folder + "frame.txt";
      final String body =
          "INDEX:           " + frame.index             + "\n" +
          "HTML LENGTH:     " + frame.html.length()     + "\n" +
          "SUBFRAME COUNT:  " + frame.frameList.size()  + "\n";
      saveFile(filename, body);

      final String htmlFilename = folder + "body.html";
      saveFile(htmlFilename, frame.html);

      final String subframeFolder = folder + "frames/";
      saveFrameList(subframeFolder, frame.frameList);
    }
  }

  public static void saveFile(final String filename, final String body) {
    saveFile(filename, body, "UTF-8");
  }

  public static void saveFile(final String filename, final String body, final String encoding) {
    try {
      saveFile(filename, body.getBytes(encoding));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void saveFile(final String filename, final byte[] body) {
    try {
      OutputStream out = null;
      try {
        createIfMissing(filename);
        out = new BufferedOutputStream(new FileOutputStream(filename));
        out.write(body);
      }
      finally {
        if (out != null)
          out.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void createIfMissing(String strFile) {
    final File f = new File(strFile);
    final File folder;

    folder = f.getParentFile();

    if (!folder.exists())
      folder.mkdirs();
  }
}
