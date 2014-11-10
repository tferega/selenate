package net.selenate.server.util

import java.io._
import org.ccil.cowan.tagsoup.{ Parser, HTMLSchema, XMLWriter }
/**
 *  (X)HTML retrieved from bank framework's front-ends will almost never
 *  adhere to the specifications, and thus we need to convert it into a
 *  standardized data source. TagSoupCleaner uses the best freely available
 *  library for cleaning up invalid (X)HTML and for converting the said
 *  source to valid XML.
 *
 *  See <a href="http://home.ccil.org/~cowan/XML/tagsoup/>TagSoup homepage</a>
 *  for more details.
 */

object TagSoupCleaner {

  def fromReader(r: Reader, w: Writer, outputEncoding: String = "UTF-8") {

    val parser = new Parser()
    val scheme = new HTMLSchema()
    parser.setProperty(Parser.schemaProperty, scheme)
    parser.setFeature(Parser.CDATAElementsFeature, false)

    val writer = new XMLWriter(w)
    writer.setOutputProperty(XMLWriter.OMIT_XML_DECLARATION, "yes")
    writer.setOutputProperty(XMLWriter.ENCODING, outputEncoding)
    writer.setPrefix(scheme.getURI(), "")
    parser.setContentHandler(writer);

    parser.setProperty(Parser.lexicalHandlerProperty, writer)

    parser.parse(new org.xml.sax.InputSource(r))
  }

  def fromStream(is: InputStream, os: OutputStream, inputEncoding: String = "UTF-8", outputEncoding: String = "UTF-8") {
    val isr = new InputStreamReader(is, inputEncoding)
    val osw = new OutputStreamWriter(os, outputEncoding)

    fromReader(isr, osw, outputEncoding)
  }

  def fromByteArray(input: Array[Byte], inputEncoding: String = "UTF-8", outputEncoding: String = "UTF-8"): Array[Byte] = {

    val bais = new ByteArrayInputStream(input)
    val baos = new ByteArrayOutputStream()

    fromStream(bais, baos, inputEncoding, outputEncoding);
    baos.toByteArray()
  }

  def fromString(input: String, outputEncoding: String = "UTF-8"): String = {

    val sr = new StringReader(input)
    val sw = new StringWriter()

    fromReader(sr, sw, outputEncoding)
    sw.toString
  }
}
