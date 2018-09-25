package io.sunshower.sdk.core.jaxb;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import org.eclipse.persistence.oxm.XMLNameTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RenamingStrategyTest {

  private XMLNameTransformer transformer;

  @BeforeEach
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
