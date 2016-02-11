package net.selenate.demo;

import net.selenate.common.actors.ActorFactory;
import net.selenate.common.comms.SePage;

import java.util.List;
import java.util.ArrayList;
import akka.util.Timeout;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;
import net.selenate.client.user.*;
import net.selenate.common.comms.res.SeResBrowserCapture;
import scala.concurrent.duration.FiniteDuration;
import static net.selenate.demo.Pages.*;

public class Demo {
  private static final String USER_ID = "";
  private static final String PASS    = "";
  private static final String QUERY   = "";

  private static final boolean RECORD_SESSION  = true;
  private static final boolean TAKE_SCREENSHOT = true;

  public static void main(String[] args) throws IOException {
    Config config = BaseConfig.config;
    ActorFactory.setConfig(config);

    final String sessionId = UUID.randomUUID().toString();
    final Timeout timeout = new Timeout(new FiniteDuration(30, TimeUnit.SECONDS));
    final String serverSystemName = "server-system";
    final String serverHost = "localhost";
    final int serverPort = 9072;
    final SessionFactory sf = new SessionFactory(timeout, serverSystemName, serverHost, serverPort);
    final Session session = sf.resumeOrCreateSession(sessionId);

    final Browser browser = new Browser(timeout, session.actor);

    try {
      final boolean isLoginOk = ebayLogin(browser, USER_ID, PASS);
      if (isLoginOk) {
        final String price = ebayGetFirstPrice(browser, QUERY);
        System.out.println("Price for " + QUERY + ": " + price);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      killSession(browser);
    }
  }

  private static boolean ebayLogin(
      final Browser browser,
      final String userID,
      final String pass) throws IOException {
    System.out.println("Opening Ebay...");
    browser.open("http://www.ebay.com/");
    browser.waitFor(Homepage.page);

    System.out.println("Opening login page...");
    browser.getElement(Homepage.login).click();
    browser.waitFor(LoginPage.page);

    System.out.println("Entering credentials and logging in...");
    browser.getElement(LoginPage.userID).setText(userID);
    browser.getElement(LoginPage.pass).setText(pass);
    browser.getElement(LoginPage.loginBtn).click();
    final List<SePage> pages = new ArrayList<>();
    pages.addAll(InvalidLoginPage.page);
    pages.addAll(LandingPage.page);
    final String openedPage = browser.waitFor(pages);
    switch (openedPage) {
    case "InvalidLoginPage":
      final String errorMessage = browser.getElement(InvalidLoginPage.errorMessage).getText();
      System.out.println("Invalid login: " + errorMessage);
      return false;
    case "LandingPage":
      System.out.println("Login successful!");
      return true;
    default:
      System.out.println("Unexpected page!");
      return false;
    }
  }

  private static String ebayGetFirstPrice(
      final Browser browser,
      final String query) throws IOException {
    System.out.println("Entering query and searching...");
    browser.getElement(LandingPage.searchBox).setText(query);
    browser.getElement(LandingPage.searchBtn).click();
    browser.waitFor(ResultsPage.page);

    System.out.println("Opening first search result...");
    browser.getElement(ResultsPage.resultLinks).click();
    browser.waitFor(ItemPage.page);

    if (TAKE_SCREENSHOT) {
      System.out.println("Capturing article screenshot and HTML");
      final SeResBrowserCapture capture = browser.capture();
      final String html = capture.getWindowList().get(0).getHtml();
      final byte[] screenshot = capture.getWindowList().get(0).getScreenshot();
      Files.write(Paths.get("V:\\capture-source.html"), html.getBytes());
      Files.write(Paths.get("V:\\capture-screenshot.png"), screenshot);
    }

    System.out.println("Scraping price...");
    final String price = browser.getElement(ItemPage.price).getText();

    return price;
  }

  private static void killSession(final Browser browser) throws IOException {
    System.out.print("Enter newline to kill session: ");
    System.in.read();

    browser.quit();
    final Timeout timeout = new Timeout(new FiniteDuration(5, TimeUnit.SECONDS));
    ActorFactory.getInstance().shutdownHook(timeout);
  }

  private final static class BaseConfig {
    public static final String HOME = getProp("user.home");
    public static final String NAME = "client";

    private BaseConfig() {
    }

    private static final Logger logger = LoggerFactory.getLogger(BaseConfig.class);

    public static final Config config = load();

    public static Timeout parseTimeout(final String raw) {
      final FiniteDuration fd = (FiniteDuration) FiniteDuration.create(raw);
      return new Timeout(fd);
    }

    private static final Config load() {
      try {
        Config config = ConfigFactory.empty().withFallback(loadAppUser()).withFallback(loadAppMain())
            .withFallback(loadAkkaUser()).withFallback(loadAkkaMain());
        logger.debug("Effective config: {}", config);
        return config;
      } catch (Exception e) {
        throw new RuntimeException(String.format("An error occured while loading configuration using name: %s!", NAME),
            e);
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
      return ConfigParseOptions.defaults().setAllowMissing(allowMissing).setSyntax(ConfigSyntax.CONF);
    }
  }
}
