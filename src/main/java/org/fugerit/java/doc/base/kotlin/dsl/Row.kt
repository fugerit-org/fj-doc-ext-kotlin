package org.fugerit.java.doc.base.kotlin.dsl

class Row : HelperDSL.TagWithText( "row" ) {
   fun cell( init: Cell.() -> Unit = {} ): Cell {
       return initTag(Cell(), init);
   }

   fun id( value: String ): Row = setAtt( this, "id", value ) { v -> v.length in 1..64 }
   fun header( value: Boolean ): Row = setAtt( this, "header", value )

}
