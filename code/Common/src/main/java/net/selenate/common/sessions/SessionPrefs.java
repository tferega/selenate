package net.selenate.common.sessions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SessionPrefs implements Serializable {
  private static final long serialVersionUID = 1L;

  private final Map<String, Object> entryList;

  public SessionPrefs() {
    entryList = new HashMap<String, Object>();
  }

  public Map<String, Object> getAll() {
    return new HashMap<String, Object>(entryList);
  }

  public void putBoolean(final String key, final boolean value) {
    if (key == null) {
      throw new IllegalArgumentException("Key cannot be null!");
    }

    entryList.put(key, value);
  }

  public void putInt(final String key, final int value) {
    if (key == null) {
      throw new IllegalArgumentException("Key cannot be null!");
    }

    entryList.put(key, value);
  }

  public void putString(final String key, final String value) {
    if (key == null) {
      throw new IllegalArgumentException("Key cannot be null!");
    }
    if (value == null) {
      throw new IllegalArgumentException("Value cannot be null!");
    }

    entryList.put(key, value);
  }
}
