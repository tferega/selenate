package net.selenate.common.exceptions;

public class SeException extends RuntimeException {
  private static final long serialVersionUID = 45749879L;

  public SeException() {
  }

  public SeException(final String message) {
    super(message);
  }

  public SeException(final Throwable cause) {
    super(cause);
  }

  public SeException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
