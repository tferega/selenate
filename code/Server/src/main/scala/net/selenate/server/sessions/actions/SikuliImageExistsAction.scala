package net.selenate.server
package sessions.actions

import extensions.SelenateFirefox
import images.Images

import net.selenate.common.comms.req.SeReqSikuliImageExists
import net.selenate.common.comms.res.SeResSikuliImageExists

class SikuliImageExistsAction(val d: SelenateFirefox) extends IAction[SeReqSikuliImageExists, SeResSikuliImageExists] {

  protected val log = Log(classOf[SikuliImageExistsAction])

  def act = { arg =>
    val isImageFound = Images.imageExists(d.parentNum, arg.image)
    new SeResSikuliImageExists(isImageFound)
  }
}
