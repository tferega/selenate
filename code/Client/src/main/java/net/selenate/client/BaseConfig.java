package net.selenate.client;

import akka.util.Timeout;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.FiniteDuration;

public final class BaseConfig {
  public static final String HOME = getProp("user.home");
  public static final String NAME = "client";
  private BaseConfig() {}

  private static final Logger logger = LoggerFactory.getLogger(BaseConfig.class);

  public static final Config config = load();

  public static Timeout parseTimeout(final String raw) {
    final FiniteDuration fd = (FiniteDuration)FiniteDuration.create(raw);
    return new Timeout(fd);
  }

  private static final Config load() {
    try {
      Config config = ConfigFactory.empty()
          .withFallback(loadAppUser())
          .withFallback(loadAppMain())
          .withFallback(loadAkkaUser())
          .withFallback(loadAkkaMain());
      logger.debug("Effective config: {}", config);
      return config;
    } catch (Exception e) {
      throw new RuntimeException(String.format("An error occured while loading configuration using name: %s!", NAME), e);
    }
  }

  private static String getProp(final String key) {
    try {
      final String value = System.getProperty(key);
      if (value == null) {
        throw new RuntimeException("A required system property is null or could not be found: " + key);
      } else {
        return value;
      }
    } catch (final Exception e) {
      throw new RuntimeException("An error occured while getting system property for key: " + key);
    }
  }

  private static Config loadAppMain() {
    final String resource = String.format("%s.reference.config", NAME);
    final Config config = ConfigFactory.parseResources(resource, getParseOpts(false));
    logger.trace("Loading main application resource config from {}: {}", resource, config);
    System.out.println(String.format("Loading main application resource config from %s: %s", resource, config));
    return config;
  }

  private static Config loadAppUser() {
    final String path = String.format("%s/.config/selenate/%s.config", HOME, NAME);
    final Config config = ConfigFactory.parseFile(new File(path), getParseOpts(true));
    logger.trace("Loading user application file config from {}: {}", path, config);
    System.out.println(String.format("Loading user application file config from %s: %s", path, config));
    return config;
  }

  private static Config loadAkkaMain() {
    final String resource = "selenate-akka.reference.config";
    final Config config = ConfigFactory.parseResources(resource, getParseOpts(false));
    logger.trace("Loading main akka resource config from {}: {}", resource, config);
    System.out.println(String.format("Loading main akka resource config from %s: %s", resource, config));
    return config;
  }

  private static Config loadAkkaUser() {
    final String path = String.format("%s/.config/selenate/%s-akka.config", HOME, NAME);
    final Config config = ConfigFactory.parseFile(new File(path), getParseOpts(true));
    logger.trace("Loading user akka file config from {}: {}", path, config);
    System.out.println(String.format("Loading user akka file config from %s: %s", path, config));
    return config;
  }

  private static ConfigParseOptions getParseOpts(final boolean allowMissing) {
    return ConfigParseOptions
      .defaults()
      .setAllowMissing(allowMissing)
      .setSyntax(ConfigSyntax.CONF);
  }
}
