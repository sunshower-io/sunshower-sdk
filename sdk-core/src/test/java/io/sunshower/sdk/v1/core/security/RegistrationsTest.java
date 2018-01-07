package io.sunshower.sdk.v1.core.security;

import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(JUnitPlatform.class)
class RegistrationsTest {

    private User user;
    private RegistrationRequestElement request;
    private RegistrationRequestElement registration;

    @BeforeEach
    public void setUp() {

        registration = Registrations.register("josiah")
                .withEmailAddress("josiah@gmail.com")
                .withPassword("password")
                .withFirstName("josiahp")
                .withLastName("haswell")
                .withPhoneNumber("970");

        user = Registrations.toUser(registration);

        request = Registrations.toElement(Registrations.toModel(registration));
    }

    @Test
    public void ensureRequestusernameIsCorrect() {
        assertThat(request.getUsername(), is("josiah"));

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
        assertThat(registration.getEmailAddress(), is("josiah@gmail.com"));
    }

    @Test
    public void ensureFieldsAreCopiedCorrectlyForPassword() {
        assertThat(registration.getPassword(), is("password"));
    }


    @Test
    public void ensureFieldsAreCopiedCorrectlyForFirstName() {
        assertThat(registration.getFirstName(), is("josiahp"));
    }

    @Test
    public void ensureFieldsAreCopiedCorrectlyForLastName() {
        assertThat(registration.getLastName(), is("haswell"));
    }


    @Test
    public void ensureFieldsAreCopiedCorrectlyForPhoneNumber() {
        assertThat(registration.getPhoneNumber(), is("970"));
    }
}