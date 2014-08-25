package net.selenate.server
package linux

import com.ferega.procrun._
import net.selenate.common.util.NamedUUID

object LinuxProc extends Loggable {
  private val uuidFactory = new NamedUUID("Process")

  def runAndEnd(command: String, arguments: Seq[Any], num: Option[Int] = None): String = {
    val uuid = uuidFactory.random
    val p = init(uuid, command, arguments, num)
    val e = p.end

    val result = e.stdOut + "\n" + e.stdErr
    logTrace(s""""$command" process $uuid finished with output "$result"""")
    result
  }

  def runAndVerify(command: String, arguments: Seq[Any], num: Option[Int] = None): Either[String, RunningProcess] = {
    val uuid = uuidFactory.random
    val p = init(uuid, command, arguments, num)
    val r = p.run
    Thread.sleep(250)
    if (r.isStopped) {
      val e = r.end
      val exitCode = e.exitCode
      val out = e.stdOut + "\n" + e.stdErr
      logWarn(s""""$command" process $uuid failed to start! Exit code: $exitCode. Output:\n$out""")
      Left(s"$command failed to start! Exit code: $exitCode. Output:\n$out")
    } else {
      logTrace(s""""$command" process $uuid started successfully""")
      Right(r)
    }
  }

  private def init(uuid: String, command: String, arguments: Seq[Any], numOpt: Option[Int]): ProcessRunner = {
    logTrace(s"""Running "$command" process $uuid with arguments [${ arguments.mkString(", ") }] ${ numOpt.map("using screen " +).getOrElse("on main screen") }""")
    numOpt match {
      case Some(num) =>
        new DisplayProcessRunner(uuid, num, command, arguments)
      case None =>
        new ProcessRunner(uuid, command, arguments)
    }
  }
}
