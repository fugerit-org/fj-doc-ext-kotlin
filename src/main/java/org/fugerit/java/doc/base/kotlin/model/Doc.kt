package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.doc.base.config.DocVersion
import org.fugerit.java.doc.base.model.DocBase

class Doc( version : String = DocVersion.CURRENT_VERSION.stringVersion() ) : HelperDSL.Tag(DocBase.TAG_NAME) {
   init {
        att( "xmlns", "http://javacoredoc.fugerit.org" )
        att( "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance" )
        att( "xsi:schemaLocation", "http://javacoredoc.fugerit.org http://www.fugerit.org/data/java/doc/xsd/doc-"+version+".xsd" )
    }
    fun head(init: Meta.() -> Unit) = initTag(Meta(), init)
    fun body(init: Body.() -> Unit) = initTag(Body(), init)
}
