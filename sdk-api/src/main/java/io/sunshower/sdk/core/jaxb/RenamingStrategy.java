package io.sunshower.sdk.core.jaxb;


import org.eclipse.persistence.oxm.XMLNameTransformer;

/**
 * Created by haswell on 5/4/17.
 */
public class RenamingStrategy implements XMLNameTransformer {


    public String transformRootElementName(String name) {
        return transformElementName(name.substring(name.lastIndexOf('.') + 1));
    }

    public String transformTypeName(String name) {
        return transformRootElementName(name);
    }

    public String transformElementName(String name) {
        StringBuilder strBldr = new StringBuilder();
        for (char character : name.toCharArray()) {
            if (Character.isUpperCase(character)) {
                strBldr.append('-');
                strBldr.append(Character.toLowerCase(character));
            } else {
                strBldr.append(character);
            }
        }
        return strBldr.toString();
    }

    public String transformAttributeName(String name) {
        return transformElementName(name);
    }

}
