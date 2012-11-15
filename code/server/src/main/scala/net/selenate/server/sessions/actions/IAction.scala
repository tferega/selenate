package net.selenate.server.sessions.actions

import org.openqa.selenium.firefox.FirefoxDriver

trait IAction[A, R] {
  val d: FirefoxDriver

  def act: (A) => R
}