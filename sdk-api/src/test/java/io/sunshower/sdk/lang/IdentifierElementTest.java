package io.sunshower.sdk.lang;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.v1.model.core.element.Value;
import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import org.junit.jupiter.api.Test;

class IdentifierElementTest extends SerializationTestCase {

  public IdentifierElementTest() {
    super(
        SerializationAware.Format.JSON,
        new Class<?>[] {Value.class, IdentifierElement.class, Identifier.class});
  }

  @Test
  public void ensureBooleanElementIsSerializedCorrectly() {
    write(Identifier.random(), System.out);
  }
}
