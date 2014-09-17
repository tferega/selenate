package net.selenate.common.exceptions;

public class SeInvalidArgumentException extends SeException {
  private static final long serialVersionUID = 45749879L;

  public SeInvalidArgumentException(String message) {
    super(message);
  }

  public SeInvalidArgumentException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
