import org.fugerit.java.doc.base.kotlin.model.doc

doc {
    head {
        info( "doc-title", "DSL Kotlin Document From Script 1" )
    }
    body {
        para( "Concise paragraph" ).align( "center" ).style( "bold" ).whiteSpaceCollapse( true ).spaceLeft( 10 )
        para {
            text("Verbose paragraph")
            align( "left" )
            style( "italic" )
            whiteSpaceCollapse( true )
            spaceAfter( 10 )
            foreColor( "#000000" )
            backColor( "#ffffff" )
        }
        phrase( "Inline phrase" ).style( "normal" ).size( 8 )
    }
}
