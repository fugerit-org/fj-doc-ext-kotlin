package org.fugerit.java.doc.base.kotlin.dsl

class Image : HelperDSL.TagWithText( "image" ) {

   fun url( value: String ): Image = setAtt( this, "url", value ) { v -> v.length in 0..2048 }
   fun type( value: String ): Image = setAtt( this, "type", value ) { v -> setOf( "png", "jpg", "gif" ).contains( v ) }
   fun scaling( value: Int ): Image = setAtt( this, "scaling", value )
   fun base64( value: String ): Image = setAtt( this, "base64", value )
   fun alt( value: String ): Image = setAtt( this, "alt", value ) { v -> v.length in 0..2048 }
   fun align( value: String ): Image = setAtt( this, "align", value ) { v -> setOf( "center", "right", "left", "justify", "justifyall" ).contains( v ) }

}
