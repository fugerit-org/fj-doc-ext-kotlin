package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.core.cfg.ConfigRuntimeException
import org.fugerit.java.core.xml.dom.DOMIO
import java.io.StringWriter

/*
 * Inspired by :
 * https://kotlinlang.org/docs/type-safe-builders.html#scope-control-dslmarker
 */

class HelperDSL {

    interface Element {
        fun render(xmlParent: org.w3c.dom.Element, xmlDocument: org.w3c.dom.Document) {
            // default do-nothing implementation
        }
    }

    class TextElement(val text: String) : Element {
        override fun render(xmlParent: org.w3c.dom.Element, xmlDocument: org.w3c.dom.Document) {
            xmlParent.appendChild( xmlDocument.createTextNode( text ) )
        }
    }

    @DslMarker
    annotation class DocTagMarker

    @DocTagMarker
    abstract class Tag(val name: String) : Element {
        val children = arrayListOf<Element>()
        val attributes = hashMapOf<String, String>()

        protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
            tag.init()
            addKid( tag )
            return tag
        }

        override fun render(xmlParent: org.w3c.dom.Element, xmlDocument: org.w3c.dom.Document) {
            val xmlElement = xmlDocument.createElement( name )
            xmlParent.appendChild( xmlElement )
            helper(xmlElement, xmlDocument)
        }

        private fun helper(xmlParent: org.w3c.dom.Element, xmlDocument: org.w3c.dom.Document) {
            attributes.forEach { a -> xmlParent.setAttribute( a.key, a.value ) }
            children.forEach { e -> e.render(xmlParent, xmlDocument) }
        }

        protected fun <T : Element> setAtt( tag : T, name : String, value: String ) : T {
            att( name, value )
            return tag
        }

        protected fun att( name : String, value: String ) {
            attributes.put( name, value )
        }

        protected fun addKid( element : Element ) {
            children.add( element )
        }

        protected fun <T : Element> setSpace( tag : T, name : String, value: Int = 0 ) : T = setAtt( tag, name, value.toString() )

        protected fun <T : Element> setColor( tag : T, name : String, value: String ) : T {
            if ( value.matches( Regex( "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$" ) ) ) {
                return setAtt( tag, name, value )
            } else {
                throw ConfigRuntimeException( "Wrong format for color : '$name'! -> '$value'!" )
            }
        }

        override fun toString(): String {
            val xmlDocument = DOMIO.newSafeDocumentBuilderFactory().newDocumentBuilder().newDocument();
            val xmlRoot = xmlDocument.createElement(name)
            helper(xmlRoot, xmlDocument)
            val writer = StringWriter()
            DOMIO.writeDOMIndent( xmlRoot, writer )
            return writer.toString()
        }

    }

    abstract class TagWithText(name: String) : Tag(name) {
        operator fun String.unaryPlus() {
            children.add(TextElement(this))
        }
    }

    abstract class BodyTag(name: String) : TagWithText(name) {
        fun para( text: String = "", init: Para.() -> Unit = {} ): Para {
            return initTag(Para( text ), init);
        }
        fun phrase( text: String = "", init: Phrase.() -> Unit = {} ): Phrase {
            return initTag(Phrase( text ), init);
        }
    }

}