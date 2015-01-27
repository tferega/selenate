package net.selenate.server.sessions.actions

import net.selenate.common.comms.SeS3Props


case class ActionContext(var useFrames: Boolean, var s3Props: Option[SeS3Props] = None) {}