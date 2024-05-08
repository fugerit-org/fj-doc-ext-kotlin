package org.fugerit.java.doc.base.kotlin.dsl

class Table : HelperDSL.TagWithText( "table" ) {
   fun row( init: Row.() -> Unit = {} ): Row {
       return initTag(Row(), init);
   }

   fun id( value: String ): Table = setAtt( this, "id", value ) { v -> v.length in 1..64 }
   fun columns( value: Int ): Table = setAtt( this, "columns", value ) { v -> v in 1..2048 }
   fun width( value: Int ): Table = setAtt( this, "width", value ) { v -> v in 1..100 }
   fun backColor( value: String ): Table = setAtt( this, "back-color", value )
   fun foreColor( value: String ): Table = setAtt( this, "fore-color", value )
   fun spacing( value: Int ): Table = setAtt( this, "spacing", value ) { v -> v in 0..2048 }
   fun padding( value: Int ): Table = setAtt( this, "padding", value ) { v -> v in 0..2048 }
   fun colwidths( value: String ): Table = setAtt( this, "colwidths", value )
   fun spaceBefore( value: Int ): Table = setAtt( this, "space-before", value ) { v -> v in 0..2048 }
   fun spaceAfter( value: Int ): Table = setAtt( this, "space-after", value ) { v -> v in 0..2048 }
   fun renderMode( value: String ): Table = setAtt( this, "render-mode", value ) { v -> setOf( "normal", "inline" ).contains( v ) }
   fun caption( value: String ): Table = setAtt( this, "caption", value )

}
