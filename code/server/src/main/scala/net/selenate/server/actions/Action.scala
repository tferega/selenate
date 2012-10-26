package net.selenate.server
package actions

import comms._

class Action extends IAction {
  def capture(): SeCapture       = ???
  def click(xpath: String): Void = ???
  def close(): Void              = ???
  def get(url: String): Boolean     = { println("GETTING URL: "+ url); true }
}