package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.doc.base.model.DocInfo

class Info( name : String, value : String ) : HelperDSL.TagWithText(DocInfo.TAG_NAME) {
    init {
        att( "name", name )
        addKid(HelperDSL.TextElement(value))
    }
}
