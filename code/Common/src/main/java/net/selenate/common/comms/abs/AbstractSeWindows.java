package net.selenate.common.comms.abs;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import com.instantor.amazon.client.data.uri.S3FileURI;

public abstract class AbstractSeWindows<W extends AbstractSeWindow<F>, F extends AbstractSeFrame<F>> implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<W>  windows;
  // TODO: make them optional elements
  private S3FileURI screenshotURI;
  private S3FileURI htmlURI;

  public AbstractSeWindows(List<W> windows){
    this.windows       = windows;
    this.screenshotURI = null;
    this.htmlURI       = null;
  }

  public AbstractSeWindows(List<W> windows, S3FileURI screenshotURI, S3FileURI htmlURI){
    this.windows       = windows;
    this.screenshotURI = screenshotURI;
    this.htmlURI       = htmlURI;
  }

  public S3FileURI getScreenshotURI() {
    return screenshotURI;
  }

  public S3FileURI getHtmlURI() {
    return htmlURI;
  }

  public List<W> getWindows() {
    return windows;
  }

  public String getAggregatedHtml() {
    return concatHtmlList(flattenWindows(windows.iterator()));
  }

  // TODO: make it smarter! on put generate BufferedImage, and finally remove screenshots if disabled!
  public byte[] getAggregatedScreenshots() throws IOException {
    int width = 0;
    int height = 0;
    List<BufferedImage> tiles = new ArrayList<BufferedImage>();
    for (Iterator<W> iter = windows.iterator(); iter.hasNext(); ) {
      byte[] bytes = iter.next().screenshot;

//      FileOutputStream fos  = new FileOutputStream(new java.io.File("/tmp/"+ java.util.UUID.randomUUID()+ ".png"));
//      fos.write(bytes);
//      fos.close();

      BufferedImage bi = ImageIO.read(new ByteArrayInputStream(bytes));
      width  += bi.getWidth();
      height += bi.getHeight();
      tiles.add(bi);
    }

    BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int i = 0; i < tiles.size(); i++) {
      BufferedImage tile = tiles.get(i);
      int prevWidth = (i == 0) ? 0 : tiles.get(i - 1).getWidth();
      combined.getGraphics().drawImage(tile, prevWidth, 0, null);
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(combined, "png", baos);
    return baos.toByteArray();
  }

  private List<String> flattenWindows(final Iterator<W> windowList) {
    final List<String> allWindowHtmlList = new ArrayList<String>();

    for (Iterator<W> iter = windows.iterator(); iter.hasNext(); ) {
      W item = iter.next();
      final String windowHtml = item.html;
      final List<String> frameHtmlList = flattenFrames(item.frameList);

      allWindowHtmlList.add(windowHtml);
      allWindowHtmlList.addAll(frameHtmlList);
    }

    return allWindowHtmlList;
  }

  private List<String> flattenFrames(final List<F> frameList) {
    final List<String> allFrameHtmlList = new ArrayList<String>();

    for (F frame : frameList) {
      final String frameHtml = frame.html;
      final List<String> childHtmlList = flattenFrames(frame.frameList);

      allFrameHtmlList.add(frameHtml);
      allFrameHtmlList.addAll(childHtmlList);
    }

    return allFrameHtmlList;
  }

  private final static String delimiter = "<pre>================================================================================</pre>";

  private String concatHtmlList(final List<String> htmlList) {
    return String.format("<html><body>%s</body></html>",
        concatWithDelimiter(delimiter, htmlList.toArray(new String[htmlList.size()])));
  }

  private String concatWithDelimiter(final String delimeter, final String... strings) {
    String ret = "";
    if (strings == null) return "";
    for (String s: strings) if(!isNullOrEmpty(s)) ret += (ret.isEmpty() ? "" : delimeter) + s;
    return ret;
  }
  private boolean isNullOrEmpty(final String s) {
    return (s == null || s.trim().isEmpty());
  }
}
