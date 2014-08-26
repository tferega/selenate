package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

trait Action[A, R] {
  val d: SelenateFirefox

  def act: (A) => R
}
