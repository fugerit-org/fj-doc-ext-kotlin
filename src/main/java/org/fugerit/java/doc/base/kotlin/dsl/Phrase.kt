package org.fugerit.java.doc.base.kotlin.dsl

class Phrase( text: String = "" ) : HelperDSL.TagWithText( "phrase" ) {

   init { setText(text) }

   fun setText( value: String ) { addKid( HelperDSL.TextElement( value ) ) }


   fun id( value: String ): Phrase = setAtt( this, "id", value ) { v -> v.length in 1..64 }
   fun fontName( value: String ): Phrase = setAtt( this, "font-name", value ) { v -> v.length in 1..64 }
   fun style( value: String ): Phrase = setAtt( this, "style", value ) { v -> setOf( "normal", "bold", "underline", "italic", "bolditalic" ).contains( v ) }
   fun leading( value: Int ): Phrase = setAtt( this, "leading", value ) { v -> v in 0..2048 }
   fun size( value: Int ): Phrase = setAtt( this, "size", value ) { v -> v in 0..256 }
   fun link( value: String ): Phrase = setAtt( this, "link", value )
   fun whiteSpaceCollapse( value: Boolean ): Phrase = setAtt( this, "white-space-collapse", value )
   fun anchor( value: String ): Phrase = setAtt( this, "anchor", value )

}
