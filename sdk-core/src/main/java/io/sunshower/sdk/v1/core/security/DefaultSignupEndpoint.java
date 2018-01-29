package io.sunshower.sdk.v1.core.security;

import io.sunshower.common.Identifier;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.lang.IdentifierElement;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.Registrations;
import io.sunshower.sdk.v1.model.core.faults.DuplicateElementException;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationConfirmationElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import io.sunshower.service.signup.RegistrationRequest;
import io.sunshower.service.signup.SignupService;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultSignupEndpoint implements SignupEndpoint {

  @Inject private Registrations registrations;
  @Inject private SignupService signupService;

  @Override
  public RegistrationConfirmationElement signup(RegistrationRequestElement request) {
    try {
      final User user = registrations.toUser(request);
      final RegistrationRequest signup = signupService.signup(user);

      return registrations.toConfirmation(signup);
    } catch (PersistenceException ex) {
      throw new DuplicateElementException("username and e-mail address must be unique");
    }
  }

  @Override
  public List<RegistrationRequestElement> list() {
    return signupService
        .pendingRegistrations()
        .stream()
        .map(registrations::toElement)
        .collect(Collectors.toList());
  }

  @Override
  public IdentifierElement approve(String id) {
    User user = signupService.approve(id);
    return new IdentifierElement(user.getId());
  }

  @Override
  public String revoke(Identifier id) {
    signupService.revoke(id);
    return id.toString();
  }
}
