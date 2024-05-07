import org.fugerit.java.doc.base.kotlin.model.docDsl

docDsl {
    head {
        info( "doc-title", "DSL Kotlin Document From Script 1" )
    }
    body {
        para( "Concise paragraph" ) {}.align( "center" ).style( "bold" )
        para {
            text("Verbose paragraph")
            align( "left" )
            style( "italic" )
        }
        para( "Inline paragraph" ).align( "right" ).style( "normal" )
    }
}
