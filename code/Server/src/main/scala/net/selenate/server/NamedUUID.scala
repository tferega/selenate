package net.selenate.server

import java.util.UUID

class NamedUUID(val name: String) {
  def random: String = s"""{$name:${ UUID.randomUUID() }}"""
}
