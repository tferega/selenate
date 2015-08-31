package net.selenate.server.actors

import java.io.File
import java.util.Date

import akka.actor.Actor
import akka.event.Logging
import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.{AgeFileFilter, TrueFileFilter}
import org.apache.commons.lang3.time.DateUtils
import scala.collection.JavaConverters._


/*
 * Only job that this actor should have is to execute housekeeping actions,
 * once called from HouseKeeping schedule method
 * 1. job
 */
sealed trait HouseKeepingJob
case object CleanFirefoxLogs extends HouseKeepingJob

class HouseKeeper extends Actor{

  private val log = Logging(context.system, this)

  def receive = {
    case CleanFirefoxLogs =>
      val oldestAllowedFileDate = DateUtils.addDays(new Date(), -7)
      log.info("Cleaning up firefox logs in /tmp/ff-downloads/logs. All files older than {} will be deleted!", oldestAllowedFileDate.toString())
      val file = new File("/tmp/ff-downloads/logs")
      if (file.isDirectory) {
        val filesToDelete = FileUtils.iterateFiles(file, new AgeFileFilter(oldestAllowedFileDate), TrueFileFilter.TRUE)
        while (filesToDelete.hasNext()){
          val f = filesToDelete.next()
          log.debug("Deleting {} file", f.getName)
          f.delete()
        }
      }

  }
}