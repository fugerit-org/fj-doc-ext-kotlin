package test.org.fugerit.java.doc.base.kotlin.model

import junit.framework.TestCase
import org.fugerit.java.doc.base.config.DocInput
import org.fugerit.java.doc.base.config.DocOutput
import org.fugerit.java.doc.base.config.DocVersion
import org.fugerit.java.doc.base.kotlin.model.Doc
import org.fugerit.java.doc.freemarker.html.FreeMarkerHtmlTypeHandlerUTF8
import org.junit.jupiter.api.Assertions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.StringReader

class DocTest : TestCase() {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun testBuild() {
        val doc = Doc(DocVersion.CURRENT_VERSION.stringVersion())
        doc.head {  }
        doc.body {
            para { text("First paragraph") }
            para { text("Second paragraph") }
            para { text("Third paragraph") }
        }
        log.info( "print doc sample \n{}", doc.toString() )
        // test rendering as html
        val handler = FreeMarkerHtmlTypeHandlerUTF8.HANDLER;
        val baos = ByteArrayOutputStream()
        val input =  DocInput.newInput( handler.type, StringReader( doc.toString() ) )
        val output =  DocOutput.newOutput( baos )
        handler.handle( input, output )
        log.info( "print html output \n{}", baos.toString() )
        Assertions.assertEquals( "http://javacoredoc.fugerit.org", doc.attributes.get( "xmlns" ) )
    }

}