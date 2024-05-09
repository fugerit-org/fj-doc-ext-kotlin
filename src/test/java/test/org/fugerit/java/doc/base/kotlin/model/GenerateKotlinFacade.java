package test.org.fugerit.java.doc.base.kotlin.model;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.function.SimpleValue;
import org.fugerit.java.core.io.FileIO;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.doc.lib.autodoc.parser.model.*;
import org.xmlet.xsdparser.xsdelements.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class GenerateKotlinFacade {

    private GenerateKotlinFacade() {}

    private static void handleRoot(GenerateKotlinConfig config, AutodocElement element ) throws IOException {
        log.info( "rootElement : {}", element.getName() );
        String fileName = config.getProperty( GenerateKotlinConfig.CONFIG_MAIN_FUN )+".kt";
        String fileNameImport = config.getProperty( GenerateKotlinConfig.CONFIG_MAIN_FUN )+"Kt";
        String rootName = config.toKotlinClass( element.getName() );
        String content = "@file:JvmName(\""+fileNameImport+"\")\n" +
                "\n" +
                "package "+config.getProperty( GenerateKotlinConfig.CONFIG_PACKAGE )+"\n" +
                "\n" +
                "fun "+ config.getProperty( GenerateKotlinConfig.CONFIG_MAIN_FUN )+"(block: "+rootName+".() -> Unit): "+rootName+" =\n" +
                "    "+rootName+"().apply(block)";
        File dslFile = new File( config.getPackageFolder(), fileName );
        FileIO.writeString( content, dslFile );
    }

    private static String createCheck( String typeFun, XsdSimpleType simpleType ) {
        String checkFun = "";
        SimpleValue<Integer> min = new SimpleValue<>(-1);
        SimpleValue<Integer> max = new SimpleValue<>(-1);
        List<String> valueEnum = new ArrayList<>();
        simpleType.getAllRestrictions().forEach( r -> {
            if ( r.getMinLength() != null ) min.setValue( r.getMinLength().getValue() );
            if ( r.getMaxLength() != null ) max.setValue( r.getMaxLength().getValue() );
            if ( r.getMinExclusive() != null ) min.setValue( Integer.valueOf( r.getMinExclusive().getValue() )+1 );
            if ( r.getMinInclusive() != null ) min.setValue( Integer.valueOf( r.getMinInclusive().getValue() ) );
            if ( r.getMaxExclusive() != null ) max.setValue( Integer.valueOf( r.getMaxExclusive().getValue() )-1 );
            if ( r.getMaxInclusive() != null ) max.setValue( Integer.valueOf( r.getMaxInclusive().getValue() ) );
            if ( r.getEnumeration() != null ) r.getEnumeration().forEach( e -> valueEnum.add( "\""+e.getValue().toString()+"\"" ) );
        } );
        if ( min.getValue() != -1 && max.getValue() != -1 ) {
            if ( typeFun.equals( "String" ) ) {
                checkFun = " { v -> v.length in "+min.getValue()+".."+max.getValue()+" }";
            } else if ( typeFun.equals( "Int" ) ) {
                checkFun = " { v -> v in "+min.getValue()+".."+max.getValue()+" }";
            }
        } else if ( !valueEnum.isEmpty() ) {
            checkFun = " { v -> setOf( " + StringUtils.concat( ", " , valueEnum ) + " ).contains( v ) }";
        }
        return checkFun;
    }

    private static void recurseElements(GenerateKotlinConfig config, AutodocElement element, final StringBuilder builder, Collection<XsdElement> elements ) {
        elements.forEach( e -> {
            boolean isTextElementSub = isTextElement( e.getXsdComplexType() );
            String kotlinClass = config.toKotlinClass( e.getRawName() );
            String kotlinFun = config.toKotlinFun( e.getRawName() );
            String textConstructor = isTextElementSub ? " text: String = \"\"," : "";
            builder.append( "   fun "+kotlinFun+"("+textConstructor+" init: "+kotlinClass+".() -> Unit = {} ): "+kotlinClass+" {\n" );
            builder.append( "       return initTag("+kotlinClass+"(" +(isTextElementSub ? "text" : "")+ "), init);\n" );
            builder.append( "   }\n" );
        } );
    }

    private static void recurseElements(GenerateKotlinConfig config, AutodocElement element, final StringBuilder builder, AutodocSequence sequence) {
        if ( sequence != null ) {
            if ( sequence.getXsdElements() != null ) recurseElements( config, element, builder, sequence.getXsdElements() );
            if ( sequence.getAutodocSequence() != null ) sequence.getAutodocSequence().forEach( c -> recurseElements( config, element, builder, c.getXsdElements() ) );
            if ( sequence.getAutodocSequence() != null ) sequence.getAutodocChoices().forEach( c -> recurseElements( config, element, builder, c.getXsdElements() ) );
        }
      }

    private static void recurseElements(GenerateKotlinConfig config, AutodocElement element, final StringBuilder builder, AutodocChoice choice) {
        if ( choice != null ) {
            if ( choice.getXsdElements() != null ) recurseElements( config, element, builder, choice.getXsdElements() );
            if ( choice.getAutodocSequence() != null ) choice.getAutodocSequence().forEach( c -> recurseElements( config, element, builder, c.getXsdElements() ) );
            if ( choice.getAutodocSequence() != null ) choice.getAutodocChoices().forEach( c -> recurseElements( config, element, builder, c.getXsdElements() ) );
        }
    }

    private static String handleElementFun( GenerateKotlinConfig config, AutodocElement element, boolean isTextElement ) {
        final StringBuilder builder = new StringBuilder();
        // set text for text element
        if ( isTextElement ) {
            builder.append( "\n   fun setText( value: String ) { addKid( HelperDSL.TextElement( value ) ) }\n\n" );
        }
        // functions for elements
        recurseElements( config, element, builder, element.getAutodocType().getSequence() );
        recurseElements( config, element, builder, element.getAutodocType().getChoice() );
        builder.append( "\n" );
        // functions for attributes
        String kotlinClassElement = config.toKotlinClass( element.getName() );
        element.getXsdAttributes().forEach( a -> {
            String kotlinkFun = config.toKotlinFun( a.getRawName() );
            String typeFun = "String";
            String checkFun = "";
            if ( a.getXsdSimpleType() != null ) {
                String baseName = AutodocUtils.getBaseName( a.getXsdSimpleType().getAllRestrictions(), config.getAutodocModel() );
                log.info( "type name : {}, basename : {}", a.getXsdSimpleType().getRawName(), baseName );
                // type
                if ( baseName.contains( "int" ) || baseName.contains( "decimal" ) ) {
                    typeFun = "Int";
                } else if ( baseName.contains( "boolean" ) ) {
                    typeFun = "Boolean";
                }
                // check
                checkFun = createCheck( typeFun, a.getXsdSimpleType() );
            }
            log.info( "element : {}, attribute : {}, type : {}", element.getName(), a.getRawName(), typeFun );
            builder.append( "   fun "+kotlinkFun+"( value: "+typeFun+" ): "+kotlinClassElement+" = setAtt( this, \""+a.getRawName()+"\", value )"+checkFun+"\n" );
        } );
        return builder.toString();
    }

    private static boolean isTextElement( XsdComplexType complexType ) {
        return complexType != null && complexType.isMixed();
    }

    private static void handleElement( GenerateKotlinConfig config, AutodocElement element ) {
        SafeFunction.apply( () -> {
            // element handling
            boolean isTextElement = isTextElement( element.getComplexType() );
            String kotlinClass = config.toKotlinClass( element.getName() );
            String textConstructor = isTextElement ? "( text: String = \"\" )" : "";
            String textInit = isTextElement ? "\n   init { setText(text) }\n" : "";
            log.info( "handleElement : {}", element.getName() );
            // root handling
            if ( element.getName().equals( config.getProperty( GenerateKotlinConfig.CONFIG_ROOT_ELEMENT ) ) ) {
                handleRoot( config, element );
                textInit = config.getProperty( GenerateKotlinConfig.CONFIG_ROOT_INIT, textInit );
            }
            String content = "package "+config.getProperty( GenerateKotlinConfig.CONFIG_PACKAGE )+"\n" +
                    "\n" +
                    "class "+kotlinClass+textConstructor+" : HelperDSL.TagWithText( \""+element.getName()+"\" ) {\n" +
                    textInit +
                    handleElementFun( config, element, isTextElement ) + "\n" +
                    "}\n";
            String fileName = kotlinClass+".kt";
            File tagFile = new File( config.getPackageFolder(), fileName );
            FileIO.writeString( content, tagFile );
        } );
    }

    public static void generate( GenerateKotlinConfig config ) {
        StringBuilder extraFun = new StringBuilder();
        if ( !config.getPackageFolder().exists() ) {
            config.getPackageFolder().mkdirs();
        }
        config.getAutodocModel().getElements().forEach( e ->  handleElement( config, e ) );
        SafeFunction.apply( () -> {
            try (InputStream is = ClassHelper.loadFromDefaultClassLoader( "generate-kotlin/helper-dsl.txt" )) {
                String helperDslContent = "package "+config.getProperty( GenerateKotlinConfig.CONFIG_PACKAGE )+"\n"
                        + StreamIO.readString( is ).replaceAll( "EXTRA-FUN", extraFun.toString() );
                File helperFile = new File( config.getPackageFolder(), "HelperDSL.kt" );
                FileIO.writeString( helperDslContent, helperFile );
            }
        } );
    }

}
