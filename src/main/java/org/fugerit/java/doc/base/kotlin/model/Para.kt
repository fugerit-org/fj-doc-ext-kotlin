package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.doc.base.model.DocPara

class Para( text: String = "" ) : HelperDSL.TagWithText(DocPara.TAG_NAME) {
    init {
        text(text)
    }
    fun para( text: String = "", init: Para.() -> Unit = {} ): Para {
        return initTag(Para( text ), init);
    }
    fun phrase( text: String = "", init: Phrase.() -> Unit = {} ): Phrase {
        return initTag(Phrase( text ), init);
    }
    fun text( value: String ) { children.add( HelperDSL.TextElement( value ) ) }
    fun id( value: String ): Para = setAtt( this,"id", value )
    fun style( value: String ): Para = setAtt( this, "style", value )
    fun align( value: String ): Para = setAtt( this,"align", value )
    fun fontName( value: String ): Para = setAtt( this, "font-name", value )
    fun leading( value: Double = 0.0 ): Para = setAtt( this,"leading", value.toString() )
    fun backColor( value: String ): Para = setColor( this,"back-color", value )
    fun foreColor( value: String ): Para = setColor( this,"fore-color", value )
    fun type( value: String ): Para = setAtt( this,"type", value )
    fun format( value: String ): Para = setAtt( this,"format", value )
    fun size( value: Int = 10 ): Para = setAtt( this,"size", value.toString() )
    fun textIndent( value: Int = 10 ): Para = setAtt( this,"textIndent", value.toString() )
    fun spaceBefore( value: Int = 0 ): Para = setSpace( this,"space-before", value )
    fun spaceAfter( value: Int = 0 ): Para = setSpace( this,"space-after", value )
    fun spaceLeft( value: Int = 0 ): Para = setSpace( this,"space-left", value )
    fun spaceRight( value: Int = 0 ): Para = setSpace( this,"space-right", value )
    fun whiteSpaceCollapse( value: Boolean = false ): Para = setAtt( this,"white-space-collapse", value.toString() )
}
