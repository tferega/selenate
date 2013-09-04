package net.selenate
package server
package sessions
package actions

import org.openqa.selenium.firefox.FirefoxDriver

trait IAction[A, R] {
  val d: FirefoxDriver

  def act: (A) => R
}
