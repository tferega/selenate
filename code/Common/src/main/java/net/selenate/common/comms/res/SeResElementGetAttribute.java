package net.selenate.common.comms.res;

import java.util.Optional;

public class SeResElementGetAttribute implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final Optional<String> value;

  public SeResElementGetAttribute(final Optional<String> value) {
    this.value = value;
  }

  public Optional<String> getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.format("SeResElementGetAttribute(%s)", value);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    SeResElementGetAttribute other = (SeResElementGetAttribute) obj;
    if (value == null) {
      if (other.value != null) return false;
    } else if (!value.equals(other.value)) return false;
    return true;
  }
}