package org.fugerit.java.doc.base.kotlin.dsl

class Bookmark( text: String = "" ) : HelperDSL.TagWithText( "bookmark" ) {

   init { setText(text) }

   fun setText( value: String ) { addKid( HelperDSL.TextElement( value ) ) }


   fun ref( value: String ): Bookmark = setAtt( this, "ref", value ) { v -> v.length in 1..64 }

}
