package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.doc.base.model.DocBase

class Doc : HelperDSL.TagWithText(DocBase.TAG_NAME) {
    fun head(init: Meta.() -> Unit) = initTag(Meta(), init)
    fun body(init: Body.() -> Unit) = initTag(Body(), init)
}
