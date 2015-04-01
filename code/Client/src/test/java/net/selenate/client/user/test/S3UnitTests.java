package net.selenate.client.user.test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.selenate.client.user.ActorBrowser;
import net.selenate.client.user.ActorFactory;
import net.selenate.common.user.Capture;
import net.selenate.common.user.ElementSelectMethod;
import net.selenate.common.user.ElementSelector;
import akka.actor.ActorRef;

import com.instantor.amazon.client.S3Client;
import com.instantor.amazon.client.IS3ClientBuilder;
/**
 * Check that you have selenate Server running
 * before you start running test cases!
 * @author ana
 */
public class S3UnitTests {
  private ActorRef session;
  private ActorBrowser browser;
  private S3Client client;


  /**
   * Sets up the test fixture.
   * (Called before every test case method.)
   * @throws IOException
   */
  @Before
  public  void setUp() throws IOException {
    System.setProperty("branch", "server");
    session = ActorFactory.getSession(java.util.UUID.randomUUID().toString(), 30);
    browser = new ActorBrowser(session);

    client = new S3Client(new IS3ClientBuilder() {
      Properties prop;
      public IS3ClientBuilder initialize() throws IOException{
        if (prop == null){
          prop = new Properties();
          FileInputStream in = new FileInputStream(System.getProperty("user.home") + "/.config/selenate/s3.config");
          prop.load(in);
          in.close();
        }
        return this;
      }
      @Override
      public String getAccessKeyID() {
        return prop.getProperty("accessKeyID");
      }
      @Override
      public String getBucketName() {
        return prop.getProperty("bucketName");
      }
      @Override
      public Logger getLogger() {
        return LoggerFactory.getLogger("S3TestLogger");
      }

      @Override
      public String getSecretAccessKey() {
        return prop.getProperty("secretAccessKey");
      }

    }.initialize());
  }


  /**
   * Tears down the test fixture.
   * (Called after every test case method.)
   * @throws IOException
   */
  @After
  public void tearDown() throws IOException {
    browser.quit();
  }

  @AfterClass
  public static void seekAndDestroy() throws IOException {
    ActorFactory.shutdown();
  }

  /**
   * Testing screenshot capture without S3 enabled
   * @throws IOException
   */
  @Test
  public void newFunctionalityShouldNotBrakeOldFunctionality() throws IOException {
    browser.open("http://www.example.com");
    Assert.assertTrue(browser.elementExists(new ElementSelector(ElementSelectMethod.TAG_NAME, "h1")));
    Capture capture = browser.capture("test");

    // URI's represent path to remote storage. If remote is not enabled, they should not exist!
    Assert.assertNull(capture.windowList.getHtmlURI());
    Assert.assertNull(capture.windowList.getScreenshotURI());

    // Selenate should be able to retrieve source binaries
    Assert.assertNotNull(capture.windowList.getWindows().get(0).screenshot);
    Assert.assertNotNull(capture.windowList.getAggregatedHtml());
  }

  @Test
  public void dataStoredShouldEqualDataRetrieved() throws IOException {
    browser.open("http://www.example.com");
    Assert.assertTrue(browser.elementExists(new ElementSelector(ElementSelectMethod.TAG_NAME, "h1")));
    // DON'T RETURN SCREENSHOTS!
    browser.setConfigureS3Client("testRealm", false);
    Capture capture1 = browser.capture("test");
    Assert.assertTrue(capture1.windowList.getWindows().get(0).screenshot.length == 0);

    // you should see something like testRealm/6c499c5e-0003-48b2-8271-038794b02d20/html/c8e9e0a5-8ff1-4d0a-bb00-74584b3bee5b
    // System.out.println(capture1.windowList.getHtmlURI().getURI());

    // RETURN SCREENSHOTS!
    browser.setConfigureS3Client("testRealm", true);
    Capture capture2   = browser.capture("test");

    byte[] screenshotFromSelenate = capture2.windowList.getWindows().get(0).screenshot;
    Assert.assertTrue(screenshotFromSelenate.length > 0);

    byte[] screenshotFromS3 = client.load(capture2.windowList.getScreenshotURI()).getBody();
    Assert.assertArrayEquals(screenshotFromSelenate, screenshotFromS3);
  }
}
