import org.fugerit.java.doc.base.kotlin.model.doc

doc {
    head {
        info( "doc-title", "DSL Kotlin Document From Script 1" )
    }
    body {
        para( "Concise paragraph" )
            .id( "first-para" )
            .fontName( "Arial" )
            .textIndent( 3 )
            .size( 8 )
            .align( "center" )
            .style( "bold" )
            .whiteSpaceCollapse( true )
            .spaceLeft( 1 ).spaceBefore( 1 ).spaceRight( 1 ).spaceAfter( 1 )
            .leading( 1 )
            .format( "{}" )
        para {
            text("Verbose paragraph")
            align( "left" )
            style( "italic" )
            whiteSpaceCollapse( true )
            spaceAfter( 10 )
            foreColor( "#000000" )
            backColor( "#ffffff" )
        }
        phrase( "Inline phrase 1" ).style( "normal" ).size( 8 )
            .fontName( "Arial" ).anchor( "a")
        phrase( "Inline phrase 2" ).link( "https://www.fugerit.org").whiteSpaceCollapse( true ).leading( 1 ).id( "inline-phrase" )
    }
}
