package org.fugerit.java.doc.base.kotlin.model

/*
 * Inspired by :
 * https://kotlinlang.org/docs/type-safe-builders.html#scope-control-dslmarker
 */

class HelperDSL {

    interface Element {
        fun render(builder: StringBuilder, indent: String)
    }

    class TextElement(val text: String) : Element {
        override fun render(builder: StringBuilder, indent: String) {
            builder.append("$indent$text\n")
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

        override fun render(builder: StringBuilder, indent: String) {
            builder.append("$indent<$name${renderAttributes()}>\n")
            for (c in children) {
                c.render(builder, indent + "  ")
            }
            builder.append("$indent</$name>\n")
        }

        private fun renderAttributes(): String {
            val builder = StringBuilder()
            for ((attr, value) in attributes) {
                builder.append(" $attr=\"$value\"")
            }
            return builder.toString()
        }

        override fun toString(): String {
            val builder = StringBuilder()
            render(builder, "")
            return builder.toString()
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