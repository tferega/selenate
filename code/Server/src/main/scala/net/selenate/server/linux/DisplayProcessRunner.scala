package net.selenate.server
package linux

import com.ferega.procrun._

object DisplayProcessRunner {
  private def displayScript(num: Int, command: String) =
    s"""|#!/bin/bash
        |DISPLAY=:$num $command "$$@"""".stripMargin

  private def createDisplayScript(num: Int, command: String) = {
    val script = displayScript(num, command)
    LinuxFile.createTempScript(script).getAbsolutePath
  }
}

class DisplayProcessRunner(uuid: String, num: Int, command: String, arguments: Seq[Any]) extends ProcessRunner(uuid, DisplayProcessRunner.createDisplayScript(num, command), arguments)
