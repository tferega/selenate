package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox

trait Action[A, R] extends Loggable {
  val sessionID: String
  override lazy val logPrefix = Some(sessionID)

  def act: (A) => R
}
