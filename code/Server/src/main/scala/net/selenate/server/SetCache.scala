package net.selenate.server

import scala.collection.mutable.HashSet

object SetCache {
  def empty[T] = new SetCache[T]()
}

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

  def checkAndReserve(elem: T) = Lock.synchronized {
    if (cache.contains(elem)) {
      true
    } else {
      cache += elem
      false
    }
  }
}
