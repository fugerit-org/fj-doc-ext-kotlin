package org.fugerit.java.doc.base.kotlin.model

fun docDsl(block: Doc.() -> Unit): Doc =
    Doc().apply(block)