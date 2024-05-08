package org.fugerit.java.doc.base.kotlin.model

import org.fugerit.java.doc.base.model.DocContainer

class Body : HelperDSL.TagWithText(DocContainer.TAG_NAME_BODY) {

    fun para( text: String = "", init: Para.() -> Unit = {} ): Para {
        return initTag(Para( text ), init);
    }
    fun phrase( text: String = "", init: Phrase.() -> Unit = {} ): Phrase {
        return initTag(Phrase( text ), init);
    }

}
