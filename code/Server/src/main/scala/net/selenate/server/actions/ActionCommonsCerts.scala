package net.selenate.server.actions

import com.ning.http.client.{ AsyncHttpClient, AsyncHttpClientConfig }
import dispatch.Http
import java.io.ByteArrayInputStream
import java.security.cert.CertificateFactory
import java.security.KeyStore
import javax.net.ssl.{ SSLContext, TrustManager, TrustManagerFactory }
import net.selenate.common.comms.SeCert
import net.selenate.server.Loggable

trait ActionCommonsCerts extends ActionCommonsBase { self: Loggable =>
  private val KSPass: Array[Char] = "KSPass".toCharArray
  private val CertFactory = CertificateFactory.getInstance("X.509")

  protected def buildSslContext(seCertList: Seq[SeCert]): SSLContext = {
    val context = SSLContext.getInstance("TLS")
    context.init(null, buildTrustManagers(seCertList), null)
    context
  }

  private def buildTrustManagers(seCertList: Seq[SeCert]): Array[TrustManager] = {
    val certStore = buildKeyStore(seCertList)
    val factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm)
    factory.init(certStore)
    factory.getTrustManagers
  }

  private def buildKeyStore(seCertList: Seq[SeCert]): KeyStore = {
    val ks = KeyStore.getInstance(KeyStore.getDefaultType)
    ks.load(null, KSPass)
    seCertList foreach addSeCert(ks)
    ks
  }

  private def addSeCert(ks: KeyStore)(seCert: SeCert): Unit = {
    val bas = new ByteArrayInputStream(seCert.getBody)
    val cert = CertFactory.generateCertificate(bas)
    ks.setCertificateEntry(seCert.getAlias, cert)
  }

  protected class SslAuthenticatingHttp(seCertList: Seq[SeCert]) extends Http {
    override val client = new AsyncHttpClient(
      (new AsyncHttpClientConfig.Builder).setSSLContext(buildSslContext(seCertList)).build
    )
  }
}
