package net.selenate.common.exceptions;

public class SeNullArgumentException extends SeException {
  private static final long serialVersionUID = 45749879L;

  public SeNullArgumentException(final String argumentName) {
    super(argumentName + " cannot be null!");
  }
}
