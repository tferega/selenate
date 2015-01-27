package net.selenate.server

import org.streum.configrity.Configuration
import com.instantor.amazon.client.S3Client
import com.instantor.amazon.client.IS3ClientBuilder
import org.slf4j.LoggerFactory

object C {
  val branch             = sys.props("branch")
  private val configPath = sys.props("user.home") / ".config" / "selenate" / branch / "server.config"
  private val config     = Configuration.load(configPath)

  object Server {
    private val serverConfig = config.detach("server")
    val poolSize          = serverConfig[Int]("pool-size")
    val host              = serverConfig[String]("host")
    val defaultProfileOpt = serverConfig.get[String]("default-profile")
  }

  object S3 {
    private val configPath = sys.props("user.home") / ".config" / "selenate" / "s3.config"
    private val s3Config   = Configuration.load(configPath)

    val bucketName      = s3Config[String]("bucketName")
    val accessKeyID     = s3Config[String]("accessKeyID")
    val secretAccessKey = s3Config[String]("secretAccessKey")
    def description(description: String) = "[S3]" + description

    lazy val client = new S3Client(new IS3ClientBuilder {
      def getBucketName()    = bucketName
      def getLogger()        = LoggerFactory.getLogger("S3Logger")
      def getAccessKeyID()   = accessKeyID
      def getSecretAccessKey = secretAccessKey
    })
  }
}
