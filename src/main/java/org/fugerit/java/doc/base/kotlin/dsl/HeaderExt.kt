package org.fugerit.java.doc.base.kotlin.dsl

class HeaderExt : HelperDSL.TagWithText( "header-ext" ) {
   fun para( text: String = "", init: Para.() -> Unit = {} ): Para {
       return initTag(Para(text), init);
   }
   fun table( init: Table.() -> Unit = {} ): Table {
       return initTag(Table(), init);
   }
   fun image( init: Image.() -> Unit = {} ): Image {
       return initTag(Image(), init);
   }
   fun phrase( text: String = "", init: Phrase.() -> Unit = {} ): Phrase {
       return initTag(Phrase(text), init);
   }
   fun barcode( init: Barcode.() -> Unit = {} ): Barcode {
       return initTag(Barcode(), init);
   }
   fun h( text: String = "", init: H.() -> Unit = {} ): H {
       return initTag(H(text), init);
   }

   fun align( value: String ): HeaderExt = setAtt( this, "align", value ) { v -> setOf( "center", "right", "left", "justify", "justifyall" ).contains( v ) }
   fun numbered( value: Boolean ): HeaderExt = setAtt( this, "numbered", value )
   fun borderWidth( value: Int ): HeaderExt = setAtt( this, "border-width", value ) { v -> v in 0..32 }
   fun expectedSize( value: Int ): HeaderExt = setAtt( this, "expected-size", value )

}
