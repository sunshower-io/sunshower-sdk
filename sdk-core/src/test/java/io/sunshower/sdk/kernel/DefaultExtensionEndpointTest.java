package io.sunshower.sdk.kernel;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

import io.sunshower.model.core.auth.Role;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.service.security.PermissionsService;
import io.sunshower.test.ws.Remote;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

class DefaultExtensionEndpointTest extends SdkTest {

  @Remote private ExtensionEndpoint extensionEndpoint;

  @Inject private PermissionsService<?> permissionsService;

  @Test
  public void ensureListingExtensionPointWorks() {
    permissionsService.impersonate(
        () -> {
          assertThat(extensionEndpoint.list().size(), is(0));
        },
        new Role("admin"));
  }
}
