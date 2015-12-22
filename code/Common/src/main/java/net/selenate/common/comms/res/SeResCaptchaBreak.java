package net.selenate.common.comms.res;

import net.selenate.common.exceptions.SeNullArgumentException;

public final class SeResCaptchaBreak implements SeCommsRes {
  private static final long serialVersionUID = 45749879L;

  private final String text;

  public SeResCaptchaBreak(final String text) {
   this.text = text;
   validate();
  }

  public String getText() {
    return text;
  }

  public SeResCaptchaBreak withText(final String newText) {
    return new SeResCaptchaBreak(newText);
  }

  private void validate() {
    if (text == null) {
      throw new SeNullArgumentException("Text");
    }
  }

  @Override
  public String toString() {
    return String.format("SeResCaptchaBreak(%s)", text);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((text == null) ? 0 : text.hashCode());
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
    SeResCaptchaBreak other = (SeResCaptchaBreak) obj;
    if (text == null) {
      if (other.text != null)
        return false;
    } else if (!text.equals(other.text))
      return false;
    return true;
  }
}
