package net.selenate.common.comms.res;

import net.selenate.common.comms.SeElement;

public final class SeResElementFind implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final SeElement element;

  public SeResElementFind(final SeElement element) {
    this.element = element;
    validate();
  }

  public SeElement getElement() {
    return element;
  }

  public SeResElementFind withElement(final SeElement newElement) {
    return new SeResElementFind(newElement);
  }

  private void validate() {
    if (element == null) {
      throw new IllegalArgumentException("Element cannot be null!");
    }
  }

  @Override
  public String toString() {
    return String.format("SeResElementFind(%s)", element);
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
    SeResElementFind other = (SeResElementFind) obj;
    if (element == null) {
      if (other.element != null)
        return false;
    } else if (!element.equals(other.element))
      return false;
    return true;
  }
}
