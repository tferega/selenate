package net.selenate.server
package sessions.actions

import driver.selenium.SelenateFirefox

trait IAction[A, R] {
  val d: SelenateFirefox

  def act: (A) => R
}
