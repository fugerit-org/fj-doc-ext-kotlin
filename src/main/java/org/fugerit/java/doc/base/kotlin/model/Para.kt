package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.doc.base.model.DocPara

class Para( text: String = "" ) : HelperDSL.TagWithText(DocPara.TAG_NAME) {
    init {
        text(text)
    }
    private fun paraAtt( name : String, value: String ): Para {
        att( name, value )
        return this
    }
    fun text( value: String ) { children.add( HelperDSL.TextElement( value ) ) }
    fun align( value: String ): Para = paraAtt( "align", value )
    fun style( value: String ): Para = paraAtt( "style", value )
}
