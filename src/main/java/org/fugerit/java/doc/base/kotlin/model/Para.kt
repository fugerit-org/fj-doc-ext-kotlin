package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.doc.base.model.DocPara

class Para( text: String = "" ) : HelperDSL.TagWithText(DocPara.TAG_NAME) {
    init {
        text(text)
    }
    fun text( value: String ) { children.add( HelperDSL.TextElement( value ) ) }
    fun id( value: String ): Para = setId( this,ConstsAtts.ID, value )
    fun style( value: String ): Para = setStyle( this, ConstsAtts.STYLE, value )
    fun align( value: String ): Para = setAlign( this,ConstsAtts.ALIGN, value )
    fun fontName( value: String ): Para = setFontName( this, ConstsAtts.FONT_NAME, value )
    fun leading( value: Int = 0 ): Para = setLeading( this, ConstsAtts.LEADING, value )
    fun backColor( value: String ): Para = setColor( this, ConstsAtts.BACK_COLOR, value )
    fun foreColor( value: String ): Para = setColor( this, ConstsAtts.FORE_COLOR, value )
    fun type( value: String ): Para = setDataType( this,ConstsAtts.TYPE, value )
    fun format( value: String ): Para = setFormat( this,ConstsAtts.FORMAT, value )
    fun size( value: Int = 10 ): Para = setFontSize( this,ConstsAtts.SIZE, value )
    fun textIndent( value: Int = 10 ): Para = setTextIndent( this,ConstsAtts.TEXT_INDENT, value )
    fun spaceBefore( value: Int = 0 ): Para = setSpace( this,ConstsAtts.SPACE_BEFORE, value )
    fun spaceAfter( value: Int = 0 ): Para = setSpace( this,ConstsAtts.SPACE_AFTER, value )
    fun spaceLeft( value: Int = 0 ): Para = setSpace( this,ConstsAtts.SPACE_LEFT, value )
    fun spaceRight( value: Int = 0 ): Para = setSpace( this,ConstsAtts.SPACE_RIGHT, value )
    fun whiteSpaceCollapse( value: Boolean = false ): Para = setAtt( this,ConstsAtts.WHITE_SPACE_COLLAPSE, value.toString() )
}
