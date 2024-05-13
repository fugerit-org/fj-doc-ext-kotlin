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
import java.util.*
import javax.script.ScriptContext
import javax.script.ScriptEngineManager

class DocAltTest : TestCase() {

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
        return testScriptWorkerBind( path, render, null );
    }

    private fun <T> testScriptWorkerBind( path : String, render : Boolean = true, bind : T ) : Doc {
        log.info( "path : {}, render : {}", path, render)
        val fileContent = File(path ).readText()
        val scriptEngine = ScriptEngineManager().getEngineByExtension("kts")
        if ( bind != null ) {
            val bindings = scriptEngine.createBindings();
            bindings.put( "data", bind );
            scriptEngine.setBindings( bindings, ScriptContext.ENGINE_SCOPE );
        }
        val parsedDsl = scriptEngine.eval(fileContent)
        if (parsedDsl !is org.fugerit.java.doc.base.kotlin.dsl.Doc) {
            throw ConfigRuntimeException("Script does not return a Doc")
        } else {
            log.info( "print doc dsl script \n{}", parsedDsl )
            if ( render ) renderHtml( parsedDsl )
        }
        return parsedDsl;
    }

    fun testScriptCoverage() = arrayListOf<String>(
        "src/test/resources/doc-dsl-sample/sample-2-coverage-a.kts",
        "src/test/resources/doc-dsl-sample/sample-2-coverage-b.kts",
        "src/test/resources/doc-dsl-sample/sample-2-coverage.kts"
    ).forEach {
        Assertions.assertEquals(
            "http://javacoredoc.fugerit.org",
            testScriptWorker("$it", false).attributes["xmlns"]
        )
    }

    fun testScript() =
        Assertions.assertEquals( "http://javacoredoc.fugerit.org",
            testScriptWorker( "src/test/resources/doc-dsl-sample/sample-2.kts" ).attributes["xmlns"]
        )

    val params = mapOf(
        "listSample" to arrayListOf<String>( "1", "2", "3", "4", "5", "6", "7", "8", "9" ),
        "testKey" to "testValue",
        "testMap" to mapOf( "nestedKey" to "nestedValue" ) )

    fun testScriptParams() =
        Assertions.assertEquals( "http://javacoredoc.fugerit.org",
            testScriptWorkerBind( "src/test/resources/doc-dsl-sample/sample-2-params.kts", true, params ).attributes["xmlns"]
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