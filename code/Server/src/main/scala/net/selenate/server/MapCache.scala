package net.selenate.server

import scala.collection.mutable.HashMap

object MapCache {
  def empty[K, V] = new MapCache[K, V]()
}

class MapCache[K, V]() {
  private case object Lock
  private val cache = HashMap.empty[K, V]

  def add(key: K, value: V) = Lock.synchronized {
    cache.put(key, value)
  }

  def remove(key: K) = Lock.synchronized {
    cache.remove(key)
  }

  def get(key: K) = Lock.synchronized {
    cache.get(key)
  }
}
