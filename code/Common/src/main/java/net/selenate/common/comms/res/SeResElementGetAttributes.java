package net.selenate.common.comms.res;

import java.util.Map;
import net.selenate.common.exceptions.SeNullArgumentException;

public final class SeResElementGetAttributes implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final Map<String, String> attributes;

  public SeResElementGetAttributes(final Map<String, String> attributes) {
    this.attributes = attributes;
    validate();
  }

  public Map<String, String> getAttributes() {
    return attributes;
  }

  public SeResElementGetAttributes withAttributes(final Map<String, String> newAttributes) {
    return new SeResElementGetAttributes(newAttributes);
  }

  private void validate() {
    if (attributes == null) {
      throw new SeNullArgumentException("Attributes");
    }
  }

  @Override
  public String toString() {
    return String.format("SeResElementGetAttributes(%s)", attributes);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
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
    SeResElementGetAttributes other = (SeResElementGetAttributes) obj;
    if (attributes == null) {
      if (other.attributes != null)
        return false;
    } else if (!attributes.equals(other.attributes))
      return false;
    return true;
  }
}
