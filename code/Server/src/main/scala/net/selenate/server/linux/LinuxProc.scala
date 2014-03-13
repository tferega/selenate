package net.selenate.server
package linux

import com.ferega.procrun._

object LinuxProc {
  def runAndEnd(command: String, arguments: Seq[Any], num: Option[Int] = None): String = {
    val p = init(command, arguments, num)
    val e = p.end

    e.stdOut + "\n" + e.stdErr
  }

  def runAndVerify(command: String, arguments: Seq[Any], num: Option[Int] = None): Either[String, RunningProcess] = {
    val p = init(command, arguments, num)
    val r = p.run
    Thread.sleep(250)
    if (r.isStopped) {
      val e = r.end
      val exitCode = e.exitCode
      val out = e.stdOut + "\n" + e.stdErr
      Left(s"$command failed to start! Exit code: $exitCode. Output:\n$out")
    } else {
      Right(r)
    }
  }

  private def init(command: String, arguments: Seq[Any], numOpt: Option[Int]): ProcessRunner = {
    numOpt match {
      case Some(num) =>
        new DisplayProcessRunner(num, command, arguments)
      case None =>
        new ProcessRunner(command, arguments)
    }
  }
}
