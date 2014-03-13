package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

trait IAction[A, R] {
  val d: SelenateFirefox

  def act: (A) => R
}
