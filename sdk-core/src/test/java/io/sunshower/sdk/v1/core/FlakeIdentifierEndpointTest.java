package io.sunshower.sdk.v1.core;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.core.IdentifierEndpoint;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.test.ws.Remote;
import org.junit.jupiter.api.Test;

class FlakeIdentifierEndpointTest extends SdkTest {

  @Remote private IdentifierEndpoint identifierEndpoint;

  @Test
  public void ensureIdentifierEndpointReturnsId() {
    Identifier id = identifierEndpoint.create();
    assertThat(id, is(not(nullValue())));
  }
}
