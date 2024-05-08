package test.org.fugerit.java.doc.base.kotlin.model

import junit.framework.TestCase
import org.fugerit.java.core.cfg.ConfigRuntimeException
import org.fugerit.java.doc.base.config.DocInput
import org.fugerit.java.doc.base.config.DocOutput
import org.fugerit.java.doc.base.kotlin.model.Doc
import org.fugerit.java.doc.base.kotlin.model.Para
import org.fugerit.java.doc.base.kotlin.model.doc
import org.fugerit.java.doc.freemarker.html.FreeMarkerHtmlTypeHandlerUTF8
import org.junit.jupiter.api.Assertions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.ObjectInputFilter.Config
import java.io.StringReader
import javax.script.ScriptEngineManager

class DocTest : TestCase() {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    private fun createString( size : Int = 1000 ) : String {
        val builder = StringBuilder();
        for ( i in 0 until size ) {
            builder.append( "0123456789" )
        }
        return builder.toString();
    }

    private val longString = createString()

    private fun createDoc(): Doc {
        return doc {
            head {
                info( "doc-title", "DSL Kotlin Document From JUnit 1" )
            }
            body {
                para( "Concise paragraph" )
                    .align( "center" )
                    .style( "bold" )
                    .whiteSpaceCollapse( true )
                    .spaceLeft( 10 )
                    .leading( 1 )
                    .format( "{}" )
                para {
                    text("Verbose paragraph")
                    align( "left" )
                    style( "italic" )
                    whiteSpaceCollapse( true )
                    spaceAfter( 10 )
                    foreColor( "#000000" )
                    backColor( "#ffffff" )
                }
                phrase( "Inline phrase" ).style( "normal" ).size( 8 )
            }
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
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.backColor( "a" ) }
        Assertions.assertThrows<ConfigRuntimeException>( ConfigRuntimeException::class.java) { testPara.foreColor( "b" ) }
    }

    fun testBuild() {
        val doc = createDoc()
        log.info( "print doc sample \n{}", doc.toString() )
        // test result
        Assertions.assertEquals( "http://javacoredoc.fugerit.org", doc.attributes.get( "xmlns" ) )
        // test rendering as html
        renderHtml( doc )
    }

    fun testScript() {
        val fileContent = File("src/test/resources/doc-dsl-sample/sample-1.kts").readText()
        val scriptEngine = ScriptEngineManager().getEngineByExtension("kts")
        val parsedDsl = scriptEngine.eval(fileContent)
        if (parsedDsl !is Doc) {
            throw Exception("Script does not return a Doc")
        } else {
            log.info( "print doc dsl script \n{}", parsedDsl )
            // test rendering as html
            renderHtml( parsedDsl )
        }
    }

}