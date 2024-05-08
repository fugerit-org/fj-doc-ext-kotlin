package test.org.fugerit.java.doc.base.kotlin.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.fugerit.java.doc.lib.autodoc.parser.model.AutodocModel;

import java.util.Properties;

@RequiredArgsConstructor
public class GenerateKotlinConfig {

    public static final String CONFIG_ROOT_ELEMENT = "root-element";

    public static final String CONFIG_SOURCE_OUTPUT_FOLDER = "source-output-folder";

    public static final String CONFIG_PACKAGE = "dsl-package";

    public static final String CONFIG_MAIN_FUN = "dsl-main-fun";

    @NonNull
    private Properties config;

    @NonNull @Getter
    private AutodocModel autodocModel;

    public String getProperty(String key) {
        return config.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }

}
