package org.fugerit.java.doc.base.kotlin.dsl

class H( text: String = "" ) : HelperDSL.TagWithText( "h" ) {

   init { setText(text) }

   fun setText( value: String ) { addKid( HelperDSL.TextElement( value ) ) }


   fun id( value: String ): H = setAtt( this, "id", value ) { v -> v.length in 1..64 }
   fun style( value: String ): H = setAtt( this, "style", value ) { v -> setOf( "normal", "bold", "underline", "italic", "bolditalic" ).contains( v ) }
   fun align( value: String ): H = setAtt( this, "align", value ) { v -> setOf( "center", "right", "left", "justify", "justifyall" ).contains( v ) }
   fun fontName( value: String ): H = setAtt( this, "font-name", value ) { v -> v.length in 1..64 }
   fun leading( value: Int ): H = setAtt( this, "leading", value ) { v -> v in 0..2048 }
   fun backColor( value: String ): H = setAtt( this, "back-color", value ) { v -> v.matches(Regex("#([A-Fa-f0-9]{6})")) }
   fun foreColor( value: String ): H = setAtt( this, "fore-color", value ) { v -> v.matches(Regex("#([A-Fa-f0-9]{6})")) }
   fun type( value: String ): H = setAtt( this, "type", value ) { v -> setOf( "string", "number", "date" ).contains( v ) }
   fun format( value: String ): H = setAtt( this, "format", value ) { v -> v.length in 1..128 }
   fun size( value: Int ): H = setAtt( this, "size", value ) { v -> v in 0..256 }
   fun textIndent( value: Int ): H = setAtt( this, "text-indent", value ) { v -> v in 0..2048 }
   fun spaceBefore( value: Int ): H = setAtt( this, "space-before", value ) { v -> v in 0..2048 }
   fun spaceAfter( value: Int ): H = setAtt( this, "space-after", value ) { v -> v in 0..2048 }
   fun spaceLeft( value: Int ): H = setAtt( this, "space-left", value ) { v -> v in 0..2048 }
   fun spaceRight( value: Int ): H = setAtt( this, "space-right", value ) { v -> v in 0..2048 }
   fun whiteSpaceCollapse( value: Boolean ): H = setAtt( this, "white-space-collapse", value )
   fun headLevel( value: Int ): H = setAtt( this, "head-level", value ) { v -> v in 1..7 }

}
