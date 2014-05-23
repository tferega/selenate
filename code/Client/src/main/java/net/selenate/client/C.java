package net.selenate.client;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public final class C {
  private C() {}

  private static final String configPath = System.getProperty("user.home") + "/" + ".config" + "/" + "selenate" + "/" + "client.config";
  public static final String ServerHost;
  public static final String ServerPort;
  public static final String ServerPath;

  static {
    try {
      final Properties config = new Properties();
      final Properties props  = System.getProperties();

      try {
        final FileInputStream pis = new FileInputStream(configPath);
        try {
          config.load(pis);
        } finally {
          pis.close();
        }
      } catch (Exception e) {
        throw new IOException(String.format("An error occured while trying to read the config file at [%s]!", configPath), e);
      }

      final String configServerHost = getNonNullProperty(config, "server.host");
      final String configServerPort = getNonNullProperty(config, "server.port");
      final String configServerPath = getNonNullProperty(config, "server.path");

      final String propsServerHost  = props.getProperty("server.host");
      final String propsServerPort  = props.getProperty("server.port");
      final String propsServerPath  = props.getProperty("server.path");

      ServerHost = orElse(propsServerHost, configServerHost);
      ServerPort = orElse(propsServerPort, configServerPort);
      ServerPath = orElse(propsServerPath, configServerPath);
    } catch (Exception e) {
      throw new RuntimeException("An error occured while loading configuration!", e);
    }
  }

  private static String getNonNullProperty(final Properties props, final String propName) {
    final String prop = props.getProperty(propName);
    if (prop == null) {
      throw new IllegalArgumentException(String.format("\"%s\" property in the config file must be defined!", propName));
    } else {
      return prop;
    }
  }

  private static String orElse(final String s1, final String s2) {
    if (s2 == null) {
      throw new NullPointerException("Second argument to orElse cannot be null!");
    }

    return (s1 == null) ? s2 : s1;
  }

  public static String getBranch() {
    return System.getProperty("branch");
  }

}
