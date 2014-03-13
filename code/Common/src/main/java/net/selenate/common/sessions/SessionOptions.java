package net.selenate.common.sessions;

import java.io.File;
import java.io.Serializable;

public class SessionOptions implements Serializable {
  private static final long serialVersionUID = 1L;

  private final boolean useFrames;
  private final SessionPrefs prefs;
  private final ISessionDisplay display;
  private final File binaryLocation;

  public SessionOptions(
      final boolean useFrames,
      final SessionPrefs prefs,
      final ISessionDisplay display,
      final File binaryLocation) {
    if (prefs == null) {
      throw new IllegalArgumentException("Preferences cannot be null!");
    }

    this.useFrames = useFrames;
    this.prefs = prefs;
    this.display = display;
    this.binaryLocation = binaryLocation;
  }

  public SessionOptions() {
    this(false, new SessionPrefs(), SessionDisplay.Main, null);
  }

  public SessionOptions(final SessionPrefs prefs) {
    this(false, prefs, SessionDisplay.Main, null);
  }

  public boolean getUseFrames() {
    return useFrames;
  }

  public SessionPrefs getPreferences() {
    return prefs;
  }

  public ISessionDisplay getDisplay() {
    return display;
  }

  public File getBinaryLocation() {
    return binaryLocation;
  }
}
