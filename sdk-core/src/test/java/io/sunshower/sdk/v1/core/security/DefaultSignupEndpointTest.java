package io.sunshower.sdk.v1.core.security;

import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.security.*;
import io.sunshower.service.signup.SignupService;
import io.sunshower.test.ws.Remote;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultSignupEndpointTest extends SdkTest {

  @Inject private SignupService signupService;

  @Remote private SignupEndpoint signupEndpoint;

  @Remote private SecurityEndpoint securityEndpoint;

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

    }
  }

  @Test
  @WithUserDetails("administrator")
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
    signupEndpoint.signup(e);

    changeSession("administrator");
    List<RegistrationRequestElement> list = signupEndpoint.list();
    assertThat(list.size(), is(1));

    RegistrationRequestElement registration = list.get(0);

    assertThat(registration.getRegistrationId(), is(not(nullValue())));
  }

  @Test
  public void
      ensureApprovingRegistrationThenAuthenticatingWithApprovedIdWorksAndReturnsCorrectRoles() {

    RegistrationRequestElement e =
        RegistrationRequestElement.newRegistration()
            .username("new-user2")
            .emailAddress("user@new2.com")
            .password("password")
            .firstName("asfafasdfcoolbeans")
            .lastName("whateverafadsfadsf")
            .phoneNumber("970-581-2131")
            .create();
    signupEndpoint.signup(e);

    changeSession("administrator");
    List<RegistrationRequestElement> list = signupEndpoint.list();
    assertThat(list.size(), is(1));
    e = list.get(0);

    changeSession("administrator");
    signupEndpoint.approve(e.getRegistrationId());

    AuthenticationElement element =
        Authenticate.as("new-user2").withPassword("password").at(securityEndpoint);
    List<RoleElement> roles = element.getPrincipal().getRoles();
    assertThat(roles.size(), is(1));
    assertThat(roles.get(0).getAuthority(), is("tenant:user"));
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
    signupEndpoint.signup(reg);

    changeSession("administrator");
    List<RegistrationRequestElement> list = signupEndpoint.list();
    assertThat(list.size(), is(1));
    RegistrationRequestElement e = list.get(0);

    changeSession("administrator");
    signupEndpoint.approve(e.getRegistrationId());

    AuthenticationElement element =
        Authenticate.as("username1").withPassword("frapper").at(securityEndpoint);

    changeSession("administrator");
    signupEndpoint.revoke(element.getPrincipal().getId());
    try {
      Authenticate.as("username1").withPassword("frapper").at(securityEndpoint);
      fail("Should've been rejected");
    } catch (NotAuthorizedException ex) {

    }
  }

  @Test
  public void ensureSubmittingDuplicatesResultsIn409() {
    try {
      RegistrationRequestElement reg =
          RegistrationRequestElement.newRegistration()
              .username("username")
              .emailAddress("joe@haswell.com")
              .password("frapper")
              .firstName("coolbeans")
              .lastName("whatever")
              .phoneNumber("970-212-9191")
              .create();
      signupEndpoint.signup(reg);
      signupEndpoint.signup(reg);
    } catch (ClientErrorException ex) {
      assertThat(ex.getResponse().getStatus(), is(409));
    }
  }
}
