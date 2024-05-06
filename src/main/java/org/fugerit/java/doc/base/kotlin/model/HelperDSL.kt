package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.core.xml.dom.DOMIO
import java.io.StringWriter

/*
 * Inspired by :
 * https://kotlinlang.org/docs/type-safe-builders.html#scope-control-dslmarker
 */

class HelperDSL {

    interface Element {
        fun render(xmlParent: org.w3c.dom.Element, xmlDocument: org.w3c.dom.Document) {}
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
            children.add(tag)
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

        fun att( name : String, value: String ) {
            attributes.put( name, value )
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
        fun para(init: Para.() -> Unit) = initTag(Para(), init)
    }

}