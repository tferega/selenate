package net.selenate.client.user.test

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import net.selenate.client.user.ActorFactory
import net.selenate.common.user.IBrowser
import java.util.UUID
import net.selenate.client.user.ActorBrowser
import net.selenate.common.user._
import org.scalatest.Matchers
import org.scalatest.Matchers._
import org.scalatest.GivenWhenThen
import java.io._

class DownloadFileSpec  extends FunSuite
                       with BeforeAndAfter
                       with Matchers
                       with GivenWhenThen {

  var browser: IBrowser = _
  val sessionID = UUID.randomUUID.toString

  before {
    val session = ActorFactory.getSession(sessionID, 30)
    browser     = new ActorBrowser(session);
  }

  test("Download action should return a binary csv file"){

    browser.open("http://datahub.io/dataset/iso-3166-1-alpha-2-country-codes/resource/9c3b30dd-f5f3-4bbe-a3cb-d7b2c21d66ce")

    val element = new ElementSelector(ElementSelectMethod.XPATH, "//a[contains(@title,'.csv')]")
    browser.waitFor(element)
    val data = browser.findElement(element).downloadFile()
    val a = data.body

    dumpToFile(new File("/tmp/output."+data.fileExtension), data.body)
    data.body.isEmpty shouldNot equal(true)

    data.fileExtension should equal("csv")
  }

  after{
    browser.quit()
    ActorFactory.shutdown()
  }

  private def dumpToFile(file: File, ba: Array[Byte]){
    val fos = new FileOutputStream(file)
    fos.write(ba)
    fos.close()
  }
}