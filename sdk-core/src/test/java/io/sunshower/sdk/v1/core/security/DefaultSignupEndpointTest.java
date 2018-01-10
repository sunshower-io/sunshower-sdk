package io.sunshower.sdk.v1.core.security;

import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.security.*;
import io.sunshower.service.signup.SignupService;
import io.sunshower.test.ws.Remote;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Disabled
public class DefaultSignupEndpointTest extends SdkTest {

//
//    @Inject
//    private SignupService signupService;
//
//
//    @Remote
//    private SignupEndpoint signupEndpoint;
//
//    @Remote
//    private SecurityEndpoint securityEndpoint;
//
//
//    @Test
//    public void ensureSignupEndpointIsInjected() {
//        assertThat(signupEndpoint, is(not(nullValue())));
//    }
//
//    @Test
//    public void ensureSignupEndpoint() {
//        assertThrows(BadRequestException.class, () -> {
//            signupEndpoint.signup(new RegistrationRequestElement());
//        });
//    }
//
//    @Test
//    public void ensureSigningUpUserWorksAndRequiresNoAuthentication() {
//        Registrations.register("usernameasdfasfasdf")
//                .withEmailAddress("joe@haswelladsfadfasdf.com")
//                .withPassword("frapper")
//                .withFirstName("coolbeans")
//                .withLastName("whatever")
//                .withPhoneNumber("970-581-1999")
//                .submit(signupEndpoint);
//
//        try {
//            signupEndpoint.list().size();
//            fail("should've not been able to do this");
//        } catch (NotAuthorizedException ex) {
//
//        }
//    }
//
//
//    @Test
//    @WithUserDetails("administrator")
//    public void ensureListingElementsWorksWhenAuthorized() {
//        Registrations.register("usernameasdfasdfasdfasdfasdf")
//                .withEmailAddress("joe2@haswellasdfasdfafs.com")
//                .withPassword("frapperasdfasdf")
//                .withFirstName("asfafasdfcoolbeans")
//                .withLastName("whateverafadsfadsf")
//                .withPhoneNumber("970-581-1212")
//                .submit(signupEndpoint);
//
//        changeSession("administrator");
//        List<RegistrationRequestElement> list = signupEndpoint.list();
//        assertThat(list.size(), is(1));
//
//        RegistrationRequestElement registration = list.get(0);
//
//        assertThat(registration.getRegistrationId(), is(not(nullValue())));
//    }
//
//
//    @Test
//    public void ensureApprovingRegistrationThenAuthenticatingWithApprovedIdWorksAndReturnsCorrectRoles() {
//
//        Registrations.register("new-user2")
//                .withEmailAddress("user@new2.com")
//                .withPassword("password")
//                .withFirstName("asfafasdfcoolbeans")
//                .withLastName("whateverafadsfadsf")
//                .withPhoneNumber("970-581-2131")
//                .submit(signupEndpoint);
//
//        changeSession("administrator");
//        List<RegistrationRequestElement> list = signupEndpoint.list();
//        assertThat(list.size(), is(1));
//        RegistrationRequestElement e = list.get(0);
//
//        changeSession("administrator");
//        signupEndpoint.approve(e.getRegistrationId());
//
//        AuthenticationElement element = Authenticate.as("new-user2")
//                .withPassword("password")
//                .at(securityEndpoint);
//        List<RoleElement> roles = element.getPrincipal().getRoles();
//        assertThat(roles.size(), is(1));
//        assertThat(roles.get(0).getAuthority(), is("tenant:user"));
//    }
//
//
//    @Test
//    public void ensureDeactivatingUserResultsInUserNotBeingAbleToAuthenticate() {
//
//
//
//        Registrations.register("new-user")
//                .withEmailAddress("user@new.com")
//                .withPassword("password")
//                .withFirstName("asfafasdfcoolbeans")
//                .withLastName("whateverafadsfadsf")
//                .withPhoneNumber("970-581-1212")
//                .submit(signupEndpoint);
//
//        changeSession("administrator");
//        List<RegistrationRequestElement> list = signupEndpoint.list();
//        assertThat(list.size(), is(1));
//        RegistrationRequestElement e = list.get(0);
//
//        changeSession("administrator");
//        signupEndpoint.approve(e.getRegistrationId());
//
//        AuthenticationElement element = Authenticate.as("new-user")
//                .withPassword("password")
//                .at(securityEndpoint);
//        changeSession("administrator");
//        signupEndpoint.revoke(element.getPrincipal().getId());
//        try {
//            element = Authenticate.as("new-user")
//                    .withPassword("password")
//                    .at(securityEndpoint);
//            fail("Should've been rejected");
//        } catch(NotAuthorizedException ex) {
//
//        }
//
//    }
//
//    @Test
//    public void ensureSubmittingDuplicatesResultsIn409() {
//
//        try {
//
//            RegistrationRequestElement reg = Registrations.register("username")
//                    .withEmailAddress("joe@haswell.com")
//                    .withPassword("frapper")
//                    .withFirstName("coolbeans")
//                    .withLastName("whatever")
//                    .withPhoneNumber("970-212-9191");
//            reg.submit(signupEndpoint);
//            reg.submit(signupEndpoint);
//        } catch (ClientErrorException ex) {
//            assertThat(ex.getResponse().getStatus(), is(409));
//        }
//    }

}