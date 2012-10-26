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
  private type K = UUID
  private type V = FirefoxDriver

  private implicit val timeout = Timeout(5 seconds)

  private sealed trait Actions
  private case object All             extends Actions
  private case class  Del(k: K)       extends Actions
  private case class  Get(k: K)       extends Actions
  private case class  Set(k: K, v: V) extends Actions

  private class CacheActor extends Actor {
    val cache: Map[K, V] = Map.empty

    def receive = {
      case All =>
        println("ALL")
        sender ! cache.keys.toIndexedSeq
      case Del(k) =>
        println("DEL(%s)".format(k))
        cache.remove(k)
      case Get(k) =>
        println("GET(%s)".format(k.toString))
        sender ! cache(k)
      case Set(k, v) =>
        println("SET(%s, %s)".format(k.toString, v.toString))
        cache(k) = v
    }
  }

  private val system = ActorSystem("session-cache")
  private val cacheActor = system.actorOf(Props[CacheActor], name = "cache-actor")

  def getKeyList: IndexedSeq[K] = {
    val f = cacheActor ? All
    val tf = f.mapTo[IndexedSeq[K]]
    Await.result(tf, 1 second)
  }

  def remove(k: K) = {
    cacheActor ! Del(k)
  }

  def apply(k: K): V = {
    val f = cacheActor ? Get(k)
    val tf = f.mapTo[V]
    Await.result(tf, 1 second)
  }

  def update(k: K, v: V) {
    cacheActor ! Set(k, v)
  }
}