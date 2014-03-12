package net.selenate.common.user;

import java.io.File;
import java.io.Serializable;

public class Options implements Serializable {
  private static final long serialVersionUID = 1L;

  private final Preferences preferences;
  private final boolean useFrames;
  private final boolean useScreen;
  private final File binaryLocation;

  public Options(
      final Preferences preferences,
      final boolean useFrames,
      final boolean useScreen,
      final File binaryLocation) {
    if (preferences == null) {
      throw new IllegalArgumentException("Preferences cannot be null!");
    }

    this.preferences = preferences;
    this.useFrames = useFrames;
    this.useScreen = useScreen;
    this.binaryLocation = binaryLocation;
  }

  public Options() {
    this(new Preferences(), false, false, null);
  }

  public Options(final Preferences preferences) {
    this(preferences, false, false, null);
  }

  public Preferences getPreferences() {
    return preferences;
  }

  public boolean getUseFrames() {
    return useFrames;
  }

  public boolean getUseScreen() {
    return useScreen;
  }

  public File getBinaryLocation() {
    return binaryLocation;
  }
}
