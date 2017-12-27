package io.sunshower.sdk.v1.core.security;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.faults.DuplicateElementException;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import io.sunshower.service.signup.SignupService;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by haswell on 5/8/17.
 */
public class DefaultSignupEndpoint implements SignupEndpoint {

    @Inject
    private SignupService signupService;


    @Override
    public void signup(RegistrationRequestElement request) {
        try {
            signupService.signup(Registrations.toUser(request));
        } catch(PersistenceException ex) {
            throw new DuplicateElementException("username and e-mail address must be unique");
        }
    }

    @Override
    public List<RegistrationRequestElement> list() {
        return  signupService
                .pendingRegistrations()
                .stream()
                .map(Registrations::toElement)
                .collect(Collectors.toList());

    }

    @Override
    public String approve(String id) {
        signupService.approve(id);
        return id;
    }

    @Override
    public String revoke(Identifier id) {
        signupService.revoke(id);
        return id.toString();
    }
}
