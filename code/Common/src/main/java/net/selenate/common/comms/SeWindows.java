package net.selenate.common.comms;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.instantor.amazon.client.data.uri.S3FileURI;

import net.selenate.common.comms.abs.AbstractSeWindows;

public class SeWindows extends AbstractSeWindows<SeWindow, SeFrame> {
  private static final long serialVersionUID = 1L;

  public SeWindows(List<SeWindow> windows) {
    super(windows);
  }

  public SeWindows(List<SeWindow> windows, S3FileURI screenshotURI, S3FileURI htmlURI) {
    super(windows, screenshotURI, htmlURI);
  }

  public byte[] getAggregatedScreenshots(final boolean returnScreenshots) throws IOException {
    if (aggregatedScreenshots != null) return aggregatedScreenshots;
    else {
      int width = 0;
      int height = 0;
      List<BufferedImage> tiles = new ArrayList<BufferedImage>();
      for (SeWindow window : windows) {
        final BufferedImage bi = ImageIO.read(new ByteArrayInputStream(window.screenshot));
        if (!returnScreenshots) {
          window.screenshot = new byte[0];
        }
        width  += bi.getWidth();
        height += bi.getHeight();
        tiles.add(bi);
      }
      this.aggregatedScreenshots = aggregateScreenshots(width, height, tiles);
      return aggregatedScreenshots;
    }
  }
}
