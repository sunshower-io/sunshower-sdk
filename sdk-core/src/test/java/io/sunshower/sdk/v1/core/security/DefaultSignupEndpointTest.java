package io.sunshower.sdk.v1.core.security;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.sunshower.core.security.InvalidCredentialException;
import io.sunshower.model.core.auth.Role;
import io.sunshower.sdk.core.ActivationEndpoint;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.faults.DuplicateElementException;
import io.sunshower.sdk.v1.model.core.security.*;
import io.sunshower.service.security.PermissionsService;
import io.sunshower.service.signup.SignupService;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class DefaultSignupEndpointTest extends SdkTest {

  @Inject private SignupService signupService;

  @Inject private SignupEndpoint signupEndpoint;

  @Inject private PermissionsService<?> permissionsService;

  @Inject private SecurityEndpoint securityEndpoint;

  @Inject private ActivationEndpoint activationEndpoint;

  @PersistenceContext private EntityManager entityManager;

  @Test
  public void ensureSignupEndpointIsInjected() {
    assertThat(signupEndpoint, is(not(nullValue())));
  }

  @Test
  public void ensureSignupEndpoint() {
    assertThrows(
        DuplicateElementException.class,
        () -> {
          signupEndpoint.signup(new RegistrationRequestElement());
        });
  }

  @Test
  public void ensureSigningUpUserWithIdsWorksAndRequiresNoAuthentication() {
    RegistrationRequestElement e =
        RegistrationRequestElement.newRegistration()
            .username("usernameasdfasfasdf")
            .emailAddress("joe@haswelladsfadfasdf.com")
            .password("frapper")
            .firstName("coolbeans")
            .lastName("whatever")
            .phoneNumber("970-581-1999")
            .products(Arrays.asList("1", "2"))
            .create();

    write(e, System.out);
    RegistrationConfirmationElement signup = signupEndpoint.signup(e);
    assertThat(signup.getPrincipal().getUsername(), is("usernameasdfasfasdf"));
    assertThat(signup.getRegistrationId(), is(not(nullValue())));
    assertThrows(
        AuthenticationCredentialsNotFoundException.class,
        () -> {
          signupEndpoint.list().size();
        });
  }

  @Test
  public void ensureSigningUpUserWorksAndRequiresNoAuthentication() {
    RegistrationRequestElement e =
        RegistrationRequestElement.newRegistration()
            .username("usernameasdfasfasdf")
            .emailAddress("joe@haswelladsfadfasdf.com")
            .password("frapper")
            .firstName("coolbeans")
            .lastName("whatever")
            .phoneNumber("970-581-1999")
            .create();
    RegistrationConfirmationElement signup = signupEndpoint.signup(e);
    assertThat(signup.getPrincipal().getUsername(), is("usernameasdfasfasdf"));
    assertThat(signup.getRegistrationId(), is(not(nullValue())));

    assertThrows(
        AuthenticationCredentialsNotFoundException.class,
        () -> {
          signupEndpoint.list().size();
        });
  }

  @Test
  public void ensureListingElementsWorksWhenAuthorized() {
    RegistrationRequestElement e =
        RegistrationRequestElement.newRegistration()
            .username("frapperfradfasdfasdfom")
            .emailAddress("joe@haswelladsfadfasdf.comasdfasf")
            .password("frapper")
            .firstName("coolbeans")
            .lastName("whatever")
            .phoneNumber("970-581-1999")
            .create();
    RegistrationConfirmationElement signup = signupEndpoint.signup(e);

    permissionsService.impersonate(
        () -> {
          try {
            List<RegistrationRequestElement> list = signupEndpoint.list();
            //            assertThat(list.size(), is(1));
            assertThat(list.size() > 0, is(true));

            RegistrationRequestElement registration = list.get(0);

            assertThat(registration.getRegistrationId(), is(not(nullValue())));
          } finally {
            signupEndpoint.delete(signup.getRegistrationId());
          }
        },
        new Role("admin"));
  }

  @Test
  public void
      ensureApprovingRegistrationThenAuthenticatingWithApprovedIdWorksAndReturnsCorrectRoles() {

    RegistrationRequestElement el =
        RegistrationRequestElement.newRegistration()
            .username("new-user2")
            .emailAddress("user@new2.com")
            .password("password")
            .firstName("asfafasdfcoolbeans")
            .lastName("whateverafadsfadsf")
            .phoneNumber("970-581-2131")
            .create();
    RegistrationConfirmationElement signup = signupEndpoint.signup(el);
    permissionsService.impersonate(
        () -> {
          List<RegistrationRequestElement> list = signupEndpoint.list();
          //            assertThat(list.size(), is(1));
          assertTrue(list.size() > 0);
          RegistrationRequestElement e =
              list.stream().filter(t -> t.getUsername().equals("new-user2")).findFirst().get();

          signupEndpoint.approve(e.getRegistrationId());

          Authenticate.as("new-user2").withPassword("password").at(securityEndpoint);
        },
        new Role("admin"));
  }

  @Test
  public void ensureDeactivatingUserResultsInUserNotBeingAbleToAuthenticate() {
    RegistrationRequestElement reg =
        RegistrationRequestElement.newRegistration()
            .username("username1")
            .emailAddress("joe1@haswell.com")
            .password("frapper")
            .firstName("coolbeans")
            .lastName("whatever")
            .phoneNumber("970-212-9191")
            .create();
    RegistrationConfirmationElement signup = signupEndpoint.signup(reg);
    permissionsService.impersonate(
        () -> {
          List<RegistrationRequestElement> list = signupEndpoint.list();
          assertThat(list.size(), is(1));
          RegistrationRequestElement e = list.get(0);

          signupEndpoint.approve(e.getRegistrationId());

          AuthenticationElement element =
              Authenticate.as("username1").withPassword("frapper").at(securityEndpoint);

          signupEndpoint.revoke(element.getPrincipal().getId());
          assertThrows(
              InvalidCredentialException.class,
              () -> {
                Authenticate.as("username1").withPassword("frapper").at(securityEndpoint);
              });
        },
        new Role("admin"));
  }

  @Test
  public void ensureSubmittingDuplicatesResultsIn409() {
    RegistrationRequestElement reg = null;
    RegistrationConfirmationElement el = null;
    reg =
        RegistrationRequestElement.newRegistration()
            .username("username")
            .emailAddress("joe@haswell.com")
            .password("frapper")
            .firstName("coolbeans")
            .lastName("whatever")
            .phoneNumber("970-212-9191")
            .create();
    el = signupEndpoint.signup(reg);
    try {
      signupEndpoint.signup(reg);
      fail("shouldn't have passed");
    } catch (DuplicateElementException ex) {

    }
  }
}
