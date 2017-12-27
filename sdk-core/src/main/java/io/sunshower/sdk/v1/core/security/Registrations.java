package io.sunshower.sdk.v1.core.security;

import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.core.security.builders.RegistrationBuilder;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import io.sunshower.service.signup.RegistrationRequest;

/**
 * Created by haswell on 5/8/17.
 */
public class Registrations {

    public static User toUser(RegistrationRequestElement request) {
        final User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.getDetails().setEmailAddress(request.getEmailAddress());
        user.getDetails().setPhoneNumber(request.getPhoneNumber());
        user.getDetails().setFirstname(request.getFirstName());
        user.getDetails().setLastname(request.getLastName());
        return user;
    }

    public static RegistrationRequestElement toElement(RegistrationRequest request) {
        final RegistrationRequestElement element = new RegistrationRequestElement();
        element.setUsername(request.getUser().getUsername());
        element.setPassword(request.getUser().getPassword());
        element.setEmailAddress(request.getUser().getDetails().getEmailAddress());
        element.setPhoneNumber(request.getUser().getDetails().getPhoneNumber());
        element.setFirstName(request.getUser().getDetails().getFirstname());
        element.setLastName(request.getUser().getDetails().getLastname());
        element.setRegistrationId(request.getRequestId());
        return element;
    }


    public static RegistrationBuilder.UsernameStep register(String username) {
        return new RegistrationBuilder.UsernameStep(username);
    }

    public static RegistrationRequest toModel(RegistrationRequestElement registration) {
        final RegistrationRequest registrationRequest = new RegistrationRequest();
        final User user = toUser(registration);
        registrationRequest.setUser(user);
        return registrationRequest;
    }
}
