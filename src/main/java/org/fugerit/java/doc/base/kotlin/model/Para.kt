package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.doc.base.model.DocPara

class Para : HelperDSL.TagWithText(DocPara.TAG_NAME) {
    fun text( text: String ) {
        val current = HelperDSL.TextElement( text );
        children.add( current )
    }
}
