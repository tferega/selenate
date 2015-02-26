package net.selenate.server

import net.selenate.server.actions.ActionCommonsFrames
import net.selenate.server.extensions.SelenateFirefox
import net.selenate.server.settings.ProfileSettings
import net.selenate.server.settings.DisplaySettings
import java.io.File
import net.selenate.server.actions.workers.BrowserWaitForAction
import net.selenate.server.actions.SessionContext
import net.selenate.common.comms.req._
import net.selenate.common.comms._
import java.util.Optional
import scala.collection.JavaConversions._
import net.selenate.server.actions.workers._
import org.apache.commons.io.FileUtils
import net.selenate.server.actions.CachedElement


object TEST {
  def main(args: Array[String]) {
    //EntryPoint.initLog
    val r = test(1 :: Nil)
r foreach println
//    if (r.hasNext) println(r.next)
  }

  def f(i: List[Int]): String = {
    println("DOING: " + i)
    i.mkString
  }


  private def test(path: List[Int]): Iterator[String] = {
    val childCount = if (path.size < 3) 2 else 0

    println(s"Executing function in $path")
    val result = f(path)
    def childrenResult =
    for {
      num    <- (1 to childCount).iterator
      result <- test(num :: path)
    } yield (result)
    childrenResult ++ Iterator.single(result)
  }
}
