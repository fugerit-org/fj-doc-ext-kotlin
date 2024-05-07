package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.doc.base.model.DocPhrase

class Phrase(text: String = "" ) : HelperDSL.TagWithText(DocPhrase.TAG_NAME) {
    init {
        text(text)
    }
    fun text( value: String ) { children.add( HelperDSL.TextElement( value ) ) }
    fun id( value: String ): Phrase = setAtt( this,"id", value )
    fun fontName( value: String ): Phrase = setAtt( this, "font-name", value )
    fun style( value: String ): Phrase = setAtt( this, "style", value )
    fun leading( value: Double = 0.0 ): Phrase = setAtt( this,"leading", value.toString() )
    fun size( value: Int = 10 ): Phrase = setAtt( this,"size", value.toString() )
    fun link( value: String ): Phrase = setAtt( this, "link", value )
    fun whiteSpaceCollapse( value: Boolean = false ): Phrase = setAtt( this,"white-space-collapse", value.toString() )
    fun anchor( value: String ): Phrase = setAtt( this, "anchor", value )
}
