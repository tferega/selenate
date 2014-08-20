package net.selenate.client;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;
import java.io.File;

public final class C {
  private C() {}

  public static final String ClientHost;
  public static final String ServerHost;
  public static final String ServerPort;
  public static final String ServerPath;

  static {
    try {
      final Config defaultConfig = loadConfig("client.reference.config");
      final Config userConfig    = loadConfig(getConfigFile());
      final Config config        = userConfig.withFallback(defaultConfig);

      ClientHost = config.getString("client.host");
      ServerHost = config.getString("server.host");
      ServerPort = config.getString("server.port");
      ServerPath = config.getString("server.path");
    } catch (Exception e) {
      throw new RuntimeException("An error occured while loading configuration!", e);
    }
  }

  private static File getConfigFile() {
    final String userHome = System.getProperty("user.home");
    final String branch   = System.getProperty("Selenate.branch");
    final File configFile;
    if (branch == null) {
      configFile = new File(userHome + "/.props/selenate/client.config");
    } else {
      configFile = new File(userHome + "/.props/selenate_" + branch + "/client.config");
    }
    return configFile;
  }

  private static ConfigParseOptions getParseOpts() {
    return ConfigParseOptions
      .defaults()
      .setAllowMissing(false)
      .setSyntax(ConfigSyntax.PROPERTIES);
  }

  private static Config loadConfig(String name) {
    return ConfigFactory.parseResources(name, getParseOpts());
  }

  private static Config loadConfig(File path) {
    return ConfigFactory.parseFile(path, getParseOpts());
  }
}
