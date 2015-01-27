package net.selenate.common.comms.abs;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractSeFrame<T extends AbstractSeFrame<T>> implements Serializable{
  private static final long serialVersionUID = 1L;
  public final String html;
  public final List<T> frameList;

  public AbstractSeFrame(
      final String html,
      final List<T> frameList) {
    this.html         = html;
    this.frameList    = frameList;
  }
}
