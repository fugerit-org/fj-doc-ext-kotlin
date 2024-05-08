package test.org.fugerit.java.doc.base.kotlin.model

import junit.framework.TestCase
import org.fugerit.java.core.cfg.ConfigRuntimeException
import org.fugerit.java.doc.base.config.DocInput
import org.fugerit.java.doc.base.config.DocOutput
import org.fugerit.java.doc.base.kotlin.dsl.Doc
import org.fugerit.java.doc.base.kotlin.dsl.Para
import org.fugerit.java.doc.base.kotlin.dsl.dslDoc
import org.fugerit.java.doc.freemarker.html.FreeMarkerHtmlTypeHandlerUTF8
import org.junit.jupiter.api.Assertions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.StringReader
import javax.script.ScriptEngineManager

class DocTestAlt : TestCase() {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    private fun createString( size : Int = 1000 ) : String {
        val builder = StringBuilder();
        for ( i in 0 until size ) {
            builder.append( "0123456789" )
        }
        return builder.toString();
    }

    private val longString = createString()

    fun testScript() {
        val fileContent = File("src/test/resources/doc-dsl-sample/sample-2.kts").readText()
        val scriptEngine = ScriptEngineManager().getEngineByExtension("kts")
        val parsedDsl = scriptEngine.eval(fileContent)
        if (parsedDsl !is org.fugerit.java.doc.base.kotlin.dsl.Doc) {
            throw Exception("Script does not return a Doc")
        } else {
            Assertions.assertEquals( "http://javacoredoc.fugerit.org", parsedDsl.attributes.get( "xmlns" ) )
            log.info( "print doc dsl script \n{}", parsedDsl )
            // test rendering as html
            renderHtml( parsedDsl )
        }
    }

    private fun renderHtml( doc: Doc ) {
        val handler = FreeMarkerHtmlTypeHandlerUTF8.HANDLER;
        val baos = ByteArrayOutputStream()
        val input =  DocInput.newInput( handler.type, StringReader( doc.toString() ) )
        val output =  DocOutput.newOutput( baos )
        handler.handle( input, output )
        log.info( "print html output \n{}", baos.toString() )
    }

    fun testFail() {
        val testPara = Para( "test paragraph" )
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.id( longString ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.spaceRight( Int.MAX_VALUE ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.spaceLeft( Int.MAX_VALUE ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.spaceBefore( Int.MAX_VALUE ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.spaceAfter( Int.MAX_VALUE ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.leading( Int.MAX_VALUE ) }
    }

}