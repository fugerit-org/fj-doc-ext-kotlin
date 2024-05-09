package org.fugerit.java.doc.base.kotlin.dsl

class Para( text: String = "" ) : HelperDSL.TagWithText( "para" ) {

   init { setText(text) }

   fun setText( value: String ) { addKid( HelperDSL.TextElement( value ) ) }

   fun phrase( text: String = "", init: Phrase.() -> Unit = {} ): Phrase {
       return initTag(Phrase(text), init);
   }
   fun para( text: String = "", init: Para.() -> Unit = {} ): Para {
       return initTag(Para(text), init);
   }

   fun id( value: String ): Para = setAtt( this, "id", value ) { v -> v.length in 1..64 }
   fun style( value: String ): Para = setAtt( this, "style", value ) { v -> setOf( "normal", "bold", "underline", "italic", "bolditalic" ).contains( v ) }
   fun align( value: String ): Para = setAtt( this, "align", value ) { v -> setOf( "center", "right", "left", "justify", "justifyall" ).contains( v ) }
   fun fontName( value: String ): Para = setAtt( this, "font-name", value ) { v -> v.length in 1..64 }
   fun leading( value: Int ): Para = setAtt( this, "leading", value ) { v -> v in 0..2048 }
   fun backColor( value: String ): Para = setAtt( this, "back-color", value ) { v -> v.matches(Regex("#([A-Fa-f0-9]{6})")) }
   fun foreColor( value: String ): Para = setAtt( this, "fore-color", value ) { v -> v.matches(Regex("#([A-Fa-f0-9]{6})")) }
   fun type( value: String ): Para = setAtt( this, "type", value ) { v -> setOf( "string", "number", "date" ).contains( v ) }
   fun format( value: String ): Para = setAtt( this, "format", value ) { v -> v.length in 1..128 }
   fun size( value: Int ): Para = setAtt( this, "size", value ) { v -> v in 0..256 }
   fun textIndent( value: Int ): Para = setAtt( this, "text-indent", value ) { v -> v in 0..2048 }
   fun spaceBefore( value: Int ): Para = setAtt( this, "space-before", value ) { v -> v in 0..2048 }
   fun spaceAfter( value: Int ): Para = setAtt( this, "space-after", value ) { v -> v in 0..2048 }
   fun spaceLeft( value: Int ): Para = setAtt( this, "space-left", value ) { v -> v in 0..2048 }
   fun spaceRight( value: Int ): Para = setAtt( this, "space-right", value ) { v -> v in 0..2048 }
   fun whiteSpaceCollapse( value: Boolean ): Para = setAtt( this, "white-space-collapse", value )

}
