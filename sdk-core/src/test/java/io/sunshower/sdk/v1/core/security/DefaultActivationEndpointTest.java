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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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
  @Transactional(propagation = Propagation.NESTED)
  public void ensureActivatingAndDeactivatingWork() {
    try {
      PrincipalElement principal = getPrincipalElement();
      ActivationElement activate = activationEndpoint.activate(principal);
      assertThat(activationEndpoint.isActive().getValue(), is(true));
      assertThat(activate.getActivator().getPassword(), is(nullValue()));
      AuthenticationRequest request =
          Authenticate.as(principal.getUsername()).withPassword(principal.getPassword());
      securityEndpoint.authenticate(request);
    } finally {
      permissionsService.impersonate(
          () -> {
            try {
              activationEndpoint.deactivate();
              assertThat(activationEndpoint.isActive().getValue(), is(false));
            } catch (Exception ex) {

            }
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
        .phoneNumber("999-999-9999")
        .password("test")
        .newPrincipal();
  }
}
