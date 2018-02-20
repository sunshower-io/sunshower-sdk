package io.sunshower.sdk.v1.core.security;

import io.sunshower.model.core.auth.Role;
import io.sunshower.sdk.core.ActivationEndpoint;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.security.*;
import io.sunshower.service.security.PermissionsService;
import io.sunshower.service.signup.SignupService;
import io.sunshower.test.ws.Remote;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DefaultSignupEndpointTest extends SdkTest {

  @Inject private SignupService signupService;

  @Remote private SignupEndpoint signupEndpoint;

  @Inject private PermissionsService<?> permissionsService;

  @Remote private SecurityEndpoint securityEndpoint;

  @Remote private ActivationEndpoint activationEndpoint;

  @PersistenceContext private EntityManager entityManager;

  @Test
  public void ensureSignupEndpointIsInjected() {
    assertThat(signupEndpoint, is(not(nullValue())));
  }

  @Test
  public void ensureSignupEndpoint() {
    assertThrows(
        BadRequestException.class,
        () -> {
          signupEndpoint.signup(new RegistrationRequestElement());
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

    try {
      signupEndpoint.list().size();
      fail("should've not been able to do this");
    } catch (NotAuthorizedException ex) {

    } finally {
      signupEndpoint.delete(signup.getRegistrationId());
    }
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
          try {
            List<RegistrationRequestElement> list = signupEndpoint.list();
            //            assertThat(list.size(), is(1));
            assertTrue(list.size() > 0);
            RegistrationRequestElement e =
                list.stream().filter(t -> t.getUsername().equals("new-user2")).findFirst().get();

            signupEndpoint.approve(e.getRegistrationId());

            AuthenticationElement element =
                Authenticate.as("new-user2").withPassword("password").at(securityEndpoint);
            List<RoleElement> roles = element.getPrincipal().getRoles();
            assertThat(roles.size(), is(1));
            assertThat(
                roles
                    .stream()
                    .map(RoleElement::getAuthority)
                    .collect(Collectors.toSet())
                    .contains("tenant:user"),
                is(true));
          } finally {
            try {
              signupEndpoint.delete(signup.getRegistrationId());
            } catch (Exception e) {

            }
          }
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
          try {
            Authenticate.as("username1").withPassword("frapper").at(securityEndpoint);
            fail("Should've been rejected");
          } catch (NotAuthorizedException ex) {

          } finally {
            try {
              signupEndpoint.delete(signup.getRegistrationId());
            } catch (Exception ex) {
              // TODO log
            }
          }
        },
        new Role("admin"));
  }

  @Test
  public void ensureSubmittingDuplicatesResultsIn409() {
    RegistrationRequestElement reg = null;
    RegistrationConfirmationElement el = null;
    try {
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
      signupEndpoint.signup(reg);
    } catch (ClientErrorException ex) {
      assertThat(ex.getResponse().getStatus(), is(409));
    } finally {
      if (el != null) {
        signupEndpoint.delete(el.getRegistrationId());
      }
    }
  }
}
