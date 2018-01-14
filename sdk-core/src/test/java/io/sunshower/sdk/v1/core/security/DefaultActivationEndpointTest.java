package io.sunshower.sdk.v1.core.security;

import io.sunshower.model.core.auth.Role;
import io.sunshower.sdk.core.ActivationEndpoint;
import io.sunshower.sdk.core.model.ActivationElement;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.model.core.security.Authenticate;
import io.sunshower.sdk.v1.model.core.security.AuthenticationRequest;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.service.security.PermissionsService;
import io.sunshower.test.ws.Remote;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class DefaultActivationEndpointTest extends SdkTest {

  @Inject private PermissionsService permissionsService;

  @Remote private SecurityEndpoint securityEndpoint;
  @Remote private ActivationEndpoint activationEndpoint;

  @Test
  public void ensureApplicationIsInitiallyInactive() {

    assertThat(activationEndpoint.isActive().getValue(), is(false));
  }

  @Test
  public void ensureActivatingAndDeactivatingWork() {

    try {
      PrincipalElement principal = getPrincipalElement();
      activationEndpoint.activate(principal);
      assertThat(activationEndpoint.isActive().getValue(), is(true));

    } finally {
      permissionsService.impersonate(
          () -> {
            activationEndpoint.deactivate();
            assertThat(activationEndpoint.isActive().getValue(), is(false));
          },
          new Role("admin"));
    }
  }

  private PrincipalElement getPrincipalElement() {
    return PrincipalElement.create()
        .active(true)
        .username("josiah")
        .emailAddress("josiah@gmail.com")
        .firstName("josiah")
        .lastName("haswell")
        .phoneNumber("970-581-3189")
        .newPrincipal();
  }
}
