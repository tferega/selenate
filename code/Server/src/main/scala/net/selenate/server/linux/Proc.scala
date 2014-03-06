package net.selenate.server.linux

import com.ferega.procrun._

object Proc {
  def runAndEnd(args: Seq[Any]): String = {
    val p = new ProcessRunner(args)
    val e = p.end

    e.stdOut + "\n" + e.stdErr
  }

  def runAndVerify(args: Seq[Any]): Either[String, RunningProcess] = {
    val p = new ProcessRunner(args)
    val command = p.command

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
}
