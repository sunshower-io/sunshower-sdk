package io.sunshower.sdk.lang;

import io.sunshower.sdk.v1.model.core.element.Value;
import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import org.junit.jupiter.api.Test;

class BooleanElementTest extends SerializationTestCase {

  public BooleanElementTest() {
    super(SerializationAware.Format.JSON, new Class<?>[] {Value.class, BooleanElement.class});
  }

  @Test
  public void ensureBooleanElementIsSerializedCorrectly() {
    write(BooleanElement.True, System.out);
  }
}
