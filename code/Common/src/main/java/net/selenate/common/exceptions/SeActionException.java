package net.selenate.common.exceptions;

import net.selenate.common.comms.req.SeCommsReq;

public class SeActionException extends SeException {
  private static final long serialVersionUID = 45749879L;

  private static String constructMessage(
      final String action,
      final SeCommsReq arg,
      final String description) {
    return String.format("An error occurred while executing %s action (%s): %s!", action, arg.toString(), description);
  }

  private static String constructMessage(
      final String action,
      final SeCommsReq arg) {
    return String.format("An error occurred while executing %s action (%s)!", action, arg.toString());
  }

  public SeActionException(
      final String action,
      final SeCommsReq arg,
      final String description) {
    super(constructMessage(action, arg, description));
  }

  public SeActionException(
      final String action,
      final SeCommsReq arg,
      final Throwable cause) {
    super(constructMessage(action, arg), cause);
  }

  public SeActionException(
      final String action,
      final SeCommsReq arg,
      final String description,
      final Throwable cause) {
    super(constructMessage(action, arg, description), cause);
  }
}
