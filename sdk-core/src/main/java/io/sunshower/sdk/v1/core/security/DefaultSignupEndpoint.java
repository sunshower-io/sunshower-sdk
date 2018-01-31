package io.sunshower.sdk.v1.core.security;

import io.sunshower.common.Identifier;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.lang.IdentifierElement;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.Registrations;
import io.sunshower.sdk.v1.model.core.Users;
import io.sunshower.sdk.v1.model.core.faults.DuplicateElementException;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationConfirmationElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import io.sunshower.service.signup.RegistrationRequest;
import io.sunshower.service.signup.SignupService;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultSignupEndpoint implements SignupEndpoint {

@PersistenceContext
private EntityManager entityManager;
  @Inject private Registrations registrations;
  @Inject private SignupService signupService;
  @Inject private Users users;

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
  @PreAuthorize("hasAuthority('admin')")
  public List<PrincipalElement> approvedUsers() {
      return entityManager.createQuery("select e from User e where e.active = true", User.class).getResultList()
              .stream().map(users::toElement).collect(Collectors.toList());
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
