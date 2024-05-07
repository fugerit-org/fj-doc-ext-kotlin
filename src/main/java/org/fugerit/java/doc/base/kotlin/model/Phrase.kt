package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.doc.base.model.DocPhrase

class Phrase(text: String = "" ) : HelperDSL.TagWithText(DocPhrase.TAG_NAME) {
    init {
        text(text)
    }
    fun text( value: String ) { children.add( HelperDSL.TextElement( value ) ) }
    fun id( value: String ): Phrase = setId( this,ConstsAtts.ID, value )
    fun fontName( value: String ): Phrase = setFontName( this, "font-name", value )
    fun style( value: String ): Phrase = setStyle( this, "style", value )
    fun leading( value: Int = 0 ): Phrase = setLeading( this,"leading", value )
    fun size( value: Int = 10 ): Phrase = setFontSize( this,"size", value  )
    fun link( value: String ): Phrase = setAtt( this, "link", value )
    fun whiteSpaceCollapse( value: Boolean = false ): Phrase = setAtt( this,"white-space-collapse", value.toString() )
    fun anchor( value: String ): Phrase = setAtt( this, "anchor", value )
}
