package net.selenate.common.exceptions;

public class SeEmptyArgumentListException extends SeException {
  private static final long serialVersionUID = 45749879L;

  public SeEmptyArgumentListException(final String argumentName) {
    super(argumentName + " cannot be empty!");
  }
}
