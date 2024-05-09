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

    private fun createDoc(): Doc {
        return dslDoc {
            meta {
                info( ( "DSL Kotlin Document From JUnit 2 with coverage" ) ).name( "doc-title" )
                header {
                    align( "center" )
                    borderWidth( 1 )
                    numbered( true )
                    expectedSize( 200 )
                }
                footer() {
                    align( "right" )
                    borderWidth( 1 )
                    numbered( true )
                    expectedSize( 200 )
                }
            }
            body {
                h( "head level 1" ).headLevel( 1 ).align( "center" )
                h( "head level 2" ).headLevel( 2 ).id( "second title" ).style( "italic" ).fontName( "Arial" ).size( 10 ).format( "{}" )
                para( "Concise paragraph" )
                    .align( "center" )
                    .style( "bold" )
                    .whiteSpaceCollapse( true )
                    .spaceLeft( 10 )
                    .leading( 1 )
                    .format( "{}" )
                para {
                    setText("Verbose paragraph")
                    align( "left" )
                    style( "italic" )
                    whiteSpaceCollapse( true )
                    spaceAfter( 10 )
                    foreColor( "#000000" )
                    backColor( "#ffffff" )

                }
                phrase( "Inline phrase" ).style( "normal" ).size( 8 )
                table {
                    row {
                        cell { para( "col 1" )  }
                        cell { para( "col 2" )  }
                        cell { para( "col 3" )  }
                    }.header( true ).id( "headerRow" )
                    row {
                        cell { para( "data 1" )  }.borderColorBottom( "#000000" ).borderColorLeft( "#000000" ).borderColorRight( "#000000" ).borderColorTop( "#ffffff" )
                        cell { para( "data 2 and 3" ).style( "italic" )  }.colspan( 2 ).rowspan( 1 ).align( "center" )
                    }.id( "firstRow" )
                }.width( 100 ).columns( 3 ).colwidths( "50;50;50" )
                barcode().size( 100 ).type( "EAN" )
                br()
                pageBreak()
            }
        }
    }

    fun testBuild() {
        val doc = createDoc()
        log.info( "print doc sample \n{}", doc.toString() )
        // test result
        Assertions.assertEquals( "http://javacoredoc.fugerit.org", doc.attributes[ "xmlns" ] )
    }

}