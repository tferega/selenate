package net.selenate.common.comms;

import net.selenate.common.exceptions.SeNullArgumentException;

public final class SeSelect implements SeComms {
  private static final long serialVersionUID = 45749879L;

  private final SeElement      element;

  public SeSelect(final SeElement element) {
    this.element        = element;
  }

  public SeElement getElement() {
    return element;
  }

  public SeSelect withElement(final SeElement newElement) {
    return new SeSelect(newElement);
  }

  public void validate() {
    if (element == null) {
      throw new SeNullArgumentException("element");
    }
  }

  @Override
  public String toString() {
    return String.format("SeSelect(%s)", element);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((element == null) ? 0 : element.hashCode());
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
    SeSelect other = (SeSelect) obj;
    if (element == null) {
      if (other.element != null)
        return false;
    } else if (!element.equals(other.element))
      return false;
    return true;
  }
}
