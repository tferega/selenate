package net.selenate
package server
package sessions
package actions

import org.openqa.selenium.remote.RemoteWebDriver

trait IAction[A, R] {
  val d: RemoteWebDriver

  def act: (A) => R
}
