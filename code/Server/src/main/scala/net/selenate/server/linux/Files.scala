package net.selenate.server.linux

import java.io.File
import org.apache.commons.io.FileUtils

object Files {
  def createTempScript(body: String) = {
    val f = File.createTempFile("temp",".sh")
    FileUtils.writeStringToFile(f, body)
    f.setExecutable(true)
    f
  }
}
