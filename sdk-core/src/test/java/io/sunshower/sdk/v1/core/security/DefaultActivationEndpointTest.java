package io.sunshower.sdk.v1.core.security;

import io.sunshower.sdk.core.ActivationEndpoint;
import io.sunshower.sdk.core.model.ActivationElement;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.model.core.security.Authenticate;
import io.sunshower.sdk.v1.model.core.security.AuthenticationRequest;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.test.ws.Remote;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class DefaultActivationEndpointTest extends SdkTest {

  @Remote private SecurityEndpoint securityEndpoint;
  @Remote private ActivationEndpoint activationEndpoint;

  @Test
  public void ensureApplicationIsInitiallyInactive() {
    assertThat(activationEndpoint.isActive().getValue(), is(false));
  }

  @Test
  public void ensureApplicationActivationWorks() {
    PrincipalElement principal =
        PrincipalElement.create()
            .active(true)
            .username("josiah")
            .emailAddress("josiah@gmail.com")
            .firstName("josiah")
            .lastName("haswell")
            .phoneNumber("970-581-3189")
            .newPrincipal();
    ActivationElement activate = activationEndpoint.activate(principal);
    assertThat(activationEndpoint.isActive().getValue(), is(true));
    AuthenticationRequest josiah =
        Authenticate.as("josiah").withPassword(activate.getTemporaryPassword());
    securityEndpoint.authenticate(josiah);
  }
  
}
