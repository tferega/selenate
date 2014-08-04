package net.selenate.server
package driver

import actors.ActorFactory
import extensions.SelenateFirefox
import info.{ PoolInfo, ProfileInfo }

object DriverPool {
  protected val log = Log(DriverPool.getClass)

  private def createPool(poolInfo: PoolInfo) = {
    val actorName = s"driver-pool_${ poolInfo.name }"
    val poolActor = ActorFactory.typed[IDriverPoolActor](actorName, new DriverPoolActor(poolInfo))
    poolInfo.name -> poolActor
  }

  private val poolMap = C.Server.Pool.poolInfoList.map(createPool).toMap
  private def findByProfile(profile: ProfileInfo): Option[String] =
    poolMap.collect {
      case(k, v) if v.profile === profile => k
    }.headOption

  def get(name: String): SelenateFirefox = {
    log.info("PoolMap by String")
    poolMap.map{ pm => log.info(pm.toString())}
    poolMap.get(name) match {
      case Some(pool) => pool.get
      case None       => throw new IllegalArgumentException(s"""Pool named "$name" does not exist.""")
    }
  }

  def get(profile: ProfileInfo): SelenateFirefox = {
    log.info("PoolMap by ProfileInfo")
    poolMap.map{ pm => log.info(pm.toString())}
    findByProfile(profile) match {
      case Some(name) => get(name)
      case None       => FirefoxRunner.run(profile)
    }
  }
}
