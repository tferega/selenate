package net.selenate.common;

import java.io.Serializable;
import java.util.UUID;
import net.selenate.common.exceptions.SeNullArgumentException;

public class NamedUUID implements Serializable {
  private static final long serialVersionUID = 1L;

  private final String name;

  public NamedUUID(final String name) {
    if (name == null) {
      throw new SeNullArgumentException("Name");
    }

    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String random() {
    final String uuid = UUID.randomUUID().toString();
    return String.format("{%s:%s}", name, uuid);
  }

  @Override
  public String toString() {
    return String.format("NamedUUID(%s)", name);
  }
}
