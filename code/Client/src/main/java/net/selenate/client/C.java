package net.selenate.client;

import com.typesafe.config.*;
import java.io.File;

public final class C {
  private C() {}

  public static final int     WaitingTimeout;
  public static final String  ClientHost;
  public static final String  ServerHost;
  public static final String  ServerPort;
  public static final Boolean RecordSessions;
  public static final Config  AkkaConfig;

  static {
    try {
      final Config defaultConfig = loadResourceConfig("client.reference.config");
      final Config userConfig    = loadFileConfig(getConfigFile());
      final Config config        = userConfig.withFallback(defaultConfig);

      WaitingTimeout = config.getInt("waiting.timeout");
      ClientHost     = config.getString("client.host");
      ServerHost     = config.getString("server.host");
      ServerPort     = config.getString("server.port");
      RecordSessions = config.getBoolean("record.sessions");
      AkkaConfig     = loadAkkaConfig(ClientHost);
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

  private static ConfigParseOptions getParseOpts(final boolean allowMissing) {
    return ConfigParseOptions
      .defaults()
      .setAllowMissing(allowMissing)
      .setSyntax(ConfigSyntax.PROPERTIES);
  }

  private static Config loadAkkaConfig(final String clientHost) {
    final ConfigValue hostnameVal = ConfigValueFactory.fromAnyRef(clientHost);
    final Config c = ConfigFactory.parseResources("akka.config").withValue("akka.remote.netty.tcp.hostname", hostnameVal);
    return c;
  }

  private static Config loadResourceConfig(String name) {
    return ConfigFactory.parseResources(name, getParseOpts(false));
  }

  private static Config loadFileConfig(File path) {
    return ConfigFactory.parseFile(path, getParseOpts(true));
  }
}
