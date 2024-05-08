package test.org.fugerit.java.doc.base.kotlin.model;

import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.doc.lib.autodoc.parser.model.AutodocElement;

public class GenerateKotlinFacade {

    private GenerateKotlinFacade() {}

    private static void handleElement(GenerateKotlinConfig config, AutodocElement element ) {

    }

    public static void generate( GenerateKotlinConfig config ) {
        StringBuilder extraFun = new StringBuilder();
        config.getAutodocModel().getElements().forEach( e ->  handleElement( config, e ) );
    }

}
