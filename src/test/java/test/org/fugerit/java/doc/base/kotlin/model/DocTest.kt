package test.org.fugerit.java.doc.base.kotlin.model

import junit.framework.TestCase
import org.fugerit.java.doc.base.config.DocInput
import org.fugerit.java.doc.base.config.DocOutput
import org.fugerit.java.doc.base.kotlin.model.Doc
import org.fugerit.java.doc.base.kotlin.model.doc
import org.fugerit.java.doc.freemarker.html.FreeMarkerHtmlTypeHandlerUTF8
import org.junit.jupiter.api.Assertions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.StringReader
import javax.script.ScriptEngineManager

class DocTest : TestCase() {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    private fun createDoc(): Doc {
        return doc {
            head {
                info( "doc-title", "DSL Kotlin Document From JUnit 1" )
            }
            body {
                para { text("First paragraph") }
                para { text("Second paragraph") }
                para { text("Third paragraph") }
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