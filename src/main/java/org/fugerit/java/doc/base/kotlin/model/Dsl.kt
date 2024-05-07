@file:JvmName("DocKt")

package org.fugerit.java.doc.base.kotlin.model

fun doc(block: Doc.() -> Unit): Doc =
    Doc().apply(block)