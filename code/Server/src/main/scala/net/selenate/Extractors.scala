package net.selenate

import server.tryo

import java.io.File

trait Extractors {
  object IsString {
    private val R = """'(.*)'"""r
    def unapply(raw: String): Option[java.lang.String] =
      tryo {
        val R(extracted) = raw
        extracted
      }
  }

  object IsInteger {
    def unapply(raw: String): Option[java.lang.Integer] = tryo(raw.toInt)
  }

  object IsInt {
    def unapply(raw: String): Option[Int] = tryo(raw.toInt)
  }

  object IsBoolean {
    def unapply(raw: String): Option[java.lang.Boolean] = tryo(raw.toBoolean)
  }

  object IsBool {
    def unapply(raw: String): Option[Boolean] = tryo(raw.toBoolean)
  }

  object IsFile {
    def unapply(raw: String): Option[File] = tryo(new File(raw))
  }
}
