package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import org.junit.jupiter.api.Test;

class AuthenticationTokenElementTest extends SerializationTestCase {

  public AuthenticationTokenElementTest() {
    super(SerializationAware.Format.JSON, new Class<?>[] {AuthenticationTokenElement.class});
  }
  
  @Test
  public void ensureSerializationIsCorrect() {
    write(new AuthenticationTokenElement("hello"), System.out);
  }
}
