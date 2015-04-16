//package net.selenate.client.user.test
//
//import org.scalatest.FunSuite
//import org.scalatest.BeforeAndAfter
//import net.selenate.client.user.ActorFactory
//import net.selenate.common.user.IBrowser
//import java.util.UUID
//import net.selenate.client.user.ActorBrowser
//import net.selenate.common.user._
//import org.scalatest.Matchers
//import org.scalatest.GivenWhenThen
//
//class FindElementSpec  extends FunSuite
//                       with BeforeAndAfter
//                       with Matchers
//                       with GivenWhenThen {
//
//  var browser: IBrowser = _
//
//  before {
//    val session = ActorFactory.getSession(UUID.randomUUID.toString, 30)
//    browser     = new ActorBrowser(session);
//  }
//
//  test("Action waitForElement should throw exception after specified timeout"){
//
//    browser.open("http://www.example.com")
//    val start = System.currentTimeMillis()
//
//    browser.waitFor(new ElementSelector(ElementSelectMethod.ID, "bogusID"))
//    val elapsed = System.currentTimeMillis() - start
//    elapsed should be >= 30000l
//  }
//
//  after{
//    browser.quit()
//    ActorFactory.shutdown()
//  }
//}