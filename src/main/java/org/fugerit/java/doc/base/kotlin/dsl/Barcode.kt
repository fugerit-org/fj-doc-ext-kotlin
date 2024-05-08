package org.fugerit.java.doc.base.kotlin.dsl

class Barcode : HelperDSL.TagWithText( "barcode" ) {

   fun size( value: Int ): Barcode = setAtt( this, "size", value ) { v -> v in 0..256 }
   fun type( value: String ): Barcode = setAtt( this, "type", value )
   fun text( value: String ): Barcode = setAtt( this, "text", value ) { v -> v.length in 0..2048 }

}
