package org.fugerit.java.doc.base.kotlin.dsl

class List : HelperDSL.TagWithText( "list" ) {
   fun li( init: Li.() -> Unit = {} ): Li {
       return initTag(Li(), init);
   }

   fun id( value: String ): List = setAtt( this, "id", value ) { v -> v.length in 1..64 }
   fun listType( value: String ): List = setAtt( this, "list-type", value ) { v -> setOf( "ul", "uld", "ulm", "ol", "oln", "oll" ).contains( v ) }

}
