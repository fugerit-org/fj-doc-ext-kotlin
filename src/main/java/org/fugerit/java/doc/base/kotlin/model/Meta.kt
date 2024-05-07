package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.doc.base.model.DocContainer

class Meta : HelperDSL.TagWithText(DocContainer.TAG_NAME_META) {
    fun info( name : String, value : String ) { addKid( Info(name, value) ) }
}
