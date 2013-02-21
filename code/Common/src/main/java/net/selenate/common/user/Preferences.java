package net.selenate.common.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Preferences implements Serializable {
  private static final long serialVersionUID = 1L;

  private final Map<String, Object> entryList;

  public Preferences() {
    entryList = new HashMap<String, Object>();
  }

  public Map<String, Object> getAll() {
    return new HashMap<String, Object>(entryList);
  }

  public void putBoolean(String key, boolean value) {
    entryList.put(key, value);
  }

  public void putInt(String key, int value) {
    entryList.put(key, value);
  }

  public void putString(String key, String value) {
    entryList.put(key, value);
  }
}
