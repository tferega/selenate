package net.selenate.common.comms.res;

import java.util.Optional;

public class SeResFindAlert implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final Optional<String> text;

  public SeResFindAlert(final Optional<String> text) {
    this.text = text;
  }

  public Optional<String> getText() {
    return text;
  }

  @Override
  public String toString() {
    return String.format("SeResFindAlert(%s)", text);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((text == null) ? 0 : text.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeResFindAlert other = (SeResFindAlert) obj;
    if (text == null) {
      if (other.text != null) return false;
    } else if (!text.equals(other.text)) return false;
    return true;
  }
}