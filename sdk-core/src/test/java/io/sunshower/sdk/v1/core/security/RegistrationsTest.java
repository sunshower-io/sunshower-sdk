package io.sunshower.sdk.v1.core.security;

import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.MappingConfiguration;
import io.sunshower.sdk.v1.model.core.Registrations;
import io.sunshower.sdk.v1.model.core.security.RegistrationConfirmationElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import io.sunshower.service.signup.RegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MappingConfiguration.class)
class RegistrationsTest {

  @Inject private Registrations registrations;

  private User user;
  private RegistrationRequestElement registration;

  @BeforeEach
  public void setUp() {
    registration =
        RegistrationRequestElement.newRegistration()
            .emailAddress("test@gmail.com")
            .password("password")
            .firstName("josiah")
            .lastName("haswell")
            .phoneNumber("frapper")
            .username("josiah")
            .create();
    user = registrations.toUser(registration);
  }

  @Test
  public void ensureIdIsMapped() {
    registration.setRegistrationId("id");
    RegistrationRequest request = registrations.toModel(registration);
    assertThat(request.getRequestId(), is("id"));
  }

  @Test
  void ensureUserIdIsSet() {

  }

  @Test
  public void ensureRegistrationTransformationToRequestWorks() {
    RegistrationRequest request = new RegistrationRequest();
    request.setUser(user);
    request.setRequestId("test");
    registration = registrations.toElement(request);
    assertThat(user, is(not(nullValue())));
    assertThat(registration.getRegistrationId(), is("test"));
    assertThat(registration.getEmailAddress(), is("test@gmail.com"));
  }

  @Test
  public void ensureRegistrationRequestPopulatesEmailAddressAndDetails() {
    RegistrationRequestElement registrationRequestElement =
        RegistrationRequestElement.newRegistration()
            .phoneNumber("hello")
            .lastName("from")
            .firstName("the")
            .password("otter")
            .emailAddress("slide")
            .username("mustve")
            .create();

    RegistrationRequest request = registrations.toModel(registrationRequestElement);
    assertThat(request.getUser().getDetails().getEmailAddress(), is("slide"));
  }

  @Test
  public void ensureEnsureToUserIsMappedcorrectly() {

    RegistrationRequestElement registrationRequestElement =
        RegistrationRequestElement.newRegistration()
            .phoneNumber("hello")
            .lastName("from")
            .firstName("the")
            .password("otter")
            .emailAddress("slide")
            .username("mustve")
            .create();
    User user = registrations.toUser(registrationRequestElement);
    assertThat(user.getDetails(), is(not(nullValue())));
    assertThat(user.getDetails().getEmailAddress(), is("slide"));
    assertThat(user.getDetails().getUser(), is(user));
    assertThat(user.getId(), is(not(nullValue())));
  }

  @Test
  public void ensureRequestusernameIsCorrect() {
    assertThat(registration.getUsername(), is("josiah"));
  }

  @Test
  public void ensureUserUsernameIsBound() {
    assertThat(user.getUsername(), is("josiah"));
  }

  @Test
  public void ensureUserPasswordIsBound() {
    assertThat(user.getPassword(), is("password"));
  }

  @Test
  public void ensureFieldsAreCopiedCorrectlyForUsername() {
    assertThat(registration.getUsername(), is("josiah"));
  }

  @Test
  public void ensureFieldsAreCopiedCorrectlyForEmail() {
    assertThat(user.getDetails().getEmailAddress(), is("test@gmail.com"));
  }

  @Test
  public void ensureFieldsAreCopiedCorrectlyForPassword() {
    assertThat(registration.getPassword(), is("password"));
  }

  @Test
  public void ensureFieldsAreCopiedCorrectlyForFirstName() {
    assertThat(registration.getFirstName(), is("josiah"));
  }

  @Test
  public void ensureFieldsAreCopiedCorrectlyForLastName() {
    assertThat(registration.getLastName(), is("haswell"));
  }

  @Test
  public void ensureFieldsAreCopiedCorrectlyForPhoneNumber() {
    assertThat(registration.getPhoneNumber(), is("frapper"));
  }

  @Test
  public void ensurRequestIdIsCopiedCorrectly() {

    final RegistrationRequest request = new RegistrationRequest();
    request.setRequestId("hello");

    RegistrationConfirmationElement element = registrations.toConfirmation(request);
    assertThat(element.getRegistrationId(), is("hello"));
  }

  @Test
  public void ensureUserIsCopiedCorrectly() {
    final RegistrationRequest request = new RegistrationRequest();
    request.setRequestId("hello");
    final User user = new User();
    user.setUsername("frapper");
    user.getDetails().setFirstname("josiah");
    user.getDetails().setLastname("haswell");
    request.setUser(user);
    RegistrationConfirmationElement element = registrations.toConfirmation(request);
    assertThat(element.getPrincipal().getUsername(), is("frapper"));
    assertThat(element.getPrincipal().getFirstName(), is("josiah"));
    assertThat(element.getPrincipal().getLastName(), is("haswell"));
  }
}
