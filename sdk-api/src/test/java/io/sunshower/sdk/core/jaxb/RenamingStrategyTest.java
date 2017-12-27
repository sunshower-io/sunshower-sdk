package io.sunshower.sdk.core.jaxb;

import org.eclipse.persistence.oxm.XMLNameTransformer;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by haswell on 5/4/17.
 */
public class RenamingStrategyTest {

    private XMLNameTransformer transformer;
    @Before
    public void setUp() {
        transformer = new RenamingStrategy();
    }
    
    @Test
    public void ensureNameTransformerTransformsCamelCaseToDashSeparated() {
        String workspaceElement = transformer.transformElementName("workspaceElement");
        assertThat(workspaceElement, is("workspace-element"));
    }

    @Test
    public void ensureAttributeNameIsTransformed() {
        String attributeName = "frapADap";
        assertThat(transformer.transformAttributeName(attributeName), is("frap-a-dap"));
    }

}