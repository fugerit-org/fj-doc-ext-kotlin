import org.fugerit.java.doc.base.kotlin.dsl.dslDoc

dslDoc {
    meta {
        info( ( "DSL Kotlin Document From JUnit 2 with coverage" ) ).name( "doc-title" )
    }
    body {
        h( "head level 1" ).headLevel( 1 ).align( "center" )
        h( "head level 2" ).headLevel( 2 ).id( "second title" ).style( "italic" ).fontName( "Arial" ).size( 10 ).format( "{}" )
        para( "Concise paragraph" )
            .align( "center" )
            .style( "bold" )
            .whiteSpaceCollapse( true )
            .spaceLeft( 10 )
            .leading( 1 )
            .format( "{}" )
        para {
            setText("Verbose paragraph")
            align( "left" )
            style( "italic" )
            whiteSpaceCollapse( true )
            spaceAfter( 10 )
            foreColor( "#000000" )
            backColor( "#ffffff" )

        }
        phrase( "Inline phrase" ).style( "normal" ).size( 8 )
        table {
            row {
                cell { para( "col 1" )  }
                cell { para( "col 2" )  }
                cell { para( "col 3" )  }
            }.header( "true" ).id( "headerRow" )
            row {
                cell { para( "data 1" )  }.borderColorBottom( "#000000" ).borderColorLeft( "#000000" ).borderColorRight( "#000000" ).borderColorTop( "#ffffff" )
                cell { para( "data 2 and 3" ).style( "italic" )  }.colspan( 2 ).rowspan( 1 ).align( "center" )
            }.id( "firstRow" )
        }.width( 100 ).columns( 3 ).colwidths( "50;50;50" )
        barcode().size( 100 ).type( "EAN" )
        br()
        pageBreak()
    }
}