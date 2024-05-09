package test.org.fugerit.java.doc.base.kotlin.model

import junit.framework.TestCase
import org.fugerit.java.core.cfg.ConfigRuntimeException
import org.fugerit.java.doc.base.config.DocInput
import org.fugerit.java.doc.base.config.DocOutput
import org.fugerit.java.doc.base.kotlin.dsl.Doc
import org.fugerit.java.doc.base.kotlin.dsl.Para
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

    private fun testScriptWorker( path : String, render : Boolean = true ) : Doc {
        log.info( "path : {}, render : {}", path, render)
        val fileContent = File(path ).readText()
        val scriptEngine = ScriptEngineManager().getEngineByExtension("kts")
        val parsedDsl = scriptEngine.eval(fileContent)
        if (parsedDsl !is org.fugerit.java.doc.base.kotlin.dsl.Doc) {
            throw ConfigRuntimeException("Script does not return a Doc")
        } else {
            log.info( "print doc dsl script \n{}", parsedDsl )
            if ( render ) renderHtml( parsedDsl )
        }
        return parsedDsl;
    }

    fun testScriptCoverage() =
        Assertions.assertEquals( "http://javacoredoc.fugerit.org",
            testScriptWorker( "src/test/resources/doc-dsl-sample/sample-2-coverage.kts" ).attributes["xmlns"]
        )

    fun testScript() =
        Assertions.assertEquals( "http://javacoredoc.fugerit.org",
            testScriptWorker( "src/test/resources/doc-dsl-sample/sample-2.kts" ).attributes["xmlns"]
        )

    private fun renderHtml( doc: Doc ) {
        val handler = FreeMarkerHtmlTypeHandlerUTF8.HANDLER;
        val buffer = ByteArrayOutputStream()
        val input =  DocInput.newInput( handler.type, StringReader( doc.toString() ) )
        val output =  DocOutput.newOutput( buffer )
        handler.handle( input, output )
        log.info( "print html output \n{}", buffer.toString() )
    }

    fun testFail() {
        val testPara = Para( "test paragraph" )
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.id( longString ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.spaceRight( Int.MAX_VALUE ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.spaceLeft( Int.MAX_VALUE ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.spaceBefore( Int.MAX_VALUE ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.spaceAfter( Int.MAX_VALUE ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.leading( Int.MAX_VALUE ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.backColor( "#a" ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.foreColor( "#b" ) }
    }

}