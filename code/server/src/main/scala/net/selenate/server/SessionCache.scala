package net.selenate.server

import akka.actor._
import akka.dispatch.Await
import akka.pattern.ask
import akka.util.duration._
import akka.util.Timeout

import java.util.UUID

import org.openqa.selenium.firefox.FirefoxDriver

import scala.collection.mutable.Map

object SessionCache {
  private type K = String
  private type V = ActorRef

  private implicit val timeout = Timeout(5 seconds)

  private sealed trait Action
  private case object All             extends Action
  private case class  Del(k: K)       extends Action
  private case class  Ctn(k: K)       extends Action
  private case class  Get(k: K)       extends Action
  private case class  Opt(k: K)       extends Action
  private case class  Set(k: K, v: V) extends Action

  private class CacheActor extends Actor {
    val cache: Map[K, V] = Map.empty

    def receive = {
      case All =>       sender ! cache.keys.toIndexedSeq
      case Del(k) =>    cache.remove(k)
      case Ctn(k) =>    sender ! cache.contains(k)
      case Get(k) =>    sender ! cache(k)
      case Opt(k) =>    sender ! cache.get(k)
      case Set(k, v) => cache(k) = v
    }
  }

  private val cacheActor = ActorFactory.untyped[CacheActor]("cache-actor")

  private def ret[T](msg: Action)(implicit m: Manifest[T]): T = {
    val f = cacheActor ? msg
    val tf = f.mapTo[T]
    Await.result(tf, 1 second)
  }

  private def snd(msg: Action) {
    cacheActor ! msg
  }

  def getKeyList         = ret[IndexedSeq[K]](All)
  def remove(k: K)       = snd(Del(k))
  def contains(k: K)     = ret[Boolean](Ctn(k))
  def apply(k: K)        = ret[V](Get(k))
  def get(k: K)          = ret[Option[V]](Opt(k))
  def update(k: K, v: V) = snd(Set(k, v))
}