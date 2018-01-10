package io.sunshower.sdk.v1.core.security;

import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.MappingConfiguration;
import io.sunshower.sdk.v1.model.core.Registrations;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
    assertThat(registration.getEmailAddress(), is("test@gmail.com"));
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
}
