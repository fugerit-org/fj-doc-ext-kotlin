package org.fugerit.java.doc.base.kotlin.dsl

class Cell : HelperDSL.TagWithText( "cell" ) {
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
   fun nbsp( init: Nbsp.() -> Unit = {} ): Nbsp {
       return initTag(Nbsp(), init);
   }
   fun br( init: Br.() -> Unit = {} ): Br {
       return initTag(Br(), init);
   }
   fun barcode( init: Barcode.() -> Unit = {} ): Barcode {
       return initTag(Barcode(), init);
   }
   fun h( text: String = "", init: H.() -> Unit = {} ): H {
       return initTag(H(text), init);
   }

   fun id( value: String ): Cell = setAtt( this, "id", value ) { v -> v.length in 1..64 }
   fun colspan( value: Int ): Cell = setAtt( this, "colspan", value )
   fun rowspan( value: Int ): Cell = setAtt( this, "rowspan", value )
   fun align( value: String ): Cell = setAtt( this, "align", value ) { v -> setOf( "center", "right", "left", "justify", "justifyall" ).contains( v ) }
   fun valign( value: String ): Cell = setAtt( this, "valign", value ) { v -> setOf( "middle", "top", "bottom" ).contains( v ) }
   fun header( value: String ): Cell = setAtt( this, "header", value )
   fun borderColor( value: String ): Cell = setAtt( this, "border-color", value ) { v -> v.matches(Regex("#([A-Fa-f0-9]{6})")) }
   fun borderColorTop( value: String ): Cell = setAtt( this, "border-color-top", value ) { v -> v.matches(Regex("#([A-Fa-f0-9]{6})")) }
   fun borderColorBottom( value: String ): Cell = setAtt( this, "border-color-bottom", value ) { v -> v.matches(Regex("#([A-Fa-f0-9]{6})")) }
   fun borderColorLeft( value: String ): Cell = setAtt( this, "border-color-left", value ) { v -> v.matches(Regex("#([A-Fa-f0-9]{6})")) }
   fun borderColorRight( value: String ): Cell = setAtt( this, "border-color-right", value ) { v -> v.matches(Regex("#([A-Fa-f0-9]{6})")) }
   fun borderWidth( value: Int ): Cell = setAtt( this, "border-width", value ) { v -> v in 0..32 }
   fun borderWidthTop( value: Int ): Cell = setAtt( this, "border-width-top", value ) { v -> v in 0..32 }
   fun borderWidthBottom( value: Int ): Cell = setAtt( this, "border-width-bottom", value ) { v -> v in 0..32 }
   fun borderWidthLeft( value: Int ): Cell = setAtt( this, "border-width-left", value ) { v -> v in 0..32 }
   fun borderWidthRight( value: Int ): Cell = setAtt( this, "border-width-right", value ) { v -> v in 0..32 }
   fun backColor( value: String ): Cell = setAtt( this, "back-color", value ) { v -> v.matches(Regex("#([A-Fa-f0-9]{6})")) }
   fun foreColor( value: String ): Cell = setAtt( this, "fore-color", value ) { v -> v.matches(Regex("#([A-Fa-f0-9]{6})")) }
   fun type( value: String ): Cell = setAtt( this, "type", value ) { v -> setOf( "string", "number", "date" ).contains( v ) }

}
