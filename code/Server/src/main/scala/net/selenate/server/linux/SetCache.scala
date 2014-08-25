package net.selenate.server.linux

import scala.collection.mutable.HashSet

class SetCache[T]() {
  private case object Lock
  private val cache = HashSet[T]()

  def add(elem: T) = Lock.synchronized {
    cache += elem
  }

  def remove(elem: T) = Lock.synchronized {
    cache -= elem
  }

  def contains(elem: T) = Lock.synchronized {
    cache.contains(elem)
  }
}
