package test.org.fugerit.java.doc.base.kotlin.model

import junit.framework.TestCase
import org.fugerit.java.doc.base.kotlin.model.Doc
import org.fugerit.java.doc.base.kotlin.model.Para
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DocTest : TestCase() {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun testBuild() {
        val doc = Doc()
        doc.head {  }
        doc.body {
            para { text("First paragraph") }
            para { text("Second paragraph") }
            para { text("Third paragraph") }
        }
        log.info( "print doc sample \n{}", doc.toString() )
    }

}