package io.sunshower.sdk.v1.core.security;

import io.sunshower.common.Identifier;
import io.sunshower.core.security.UserService;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.lang.IdentifierElement;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.Registrations;
import io.sunshower.sdk.v1.model.core.Users;
import io.sunshower.sdk.v1.model.core.faults.DuplicateElementException;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationConfirmationElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import io.sunshower.service.ext.IconService;
import io.sunshower.service.signup.RegistrationRequest;
import io.sunshower.service.signup.SignupService;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DefaultSignupEndpoint implements SignupEndpoint {

  @Inject private Users users;
  @Inject private Registrations registrations;
  @Inject private SignupService signupService;
  @Inject private IconService iconService;
  @Inject private UserService userService;
  @PersistenceContext private EntityManager entityManager;

  @Override
  public RegistrationConfirmationElement signup(RegistrationRequestElement request) {
    List<String> productIds =
        request.getProducts() == null ? Collections.emptyList() : request.getProducts();
    try {
      final User user = registrations.toUser(request);
      checkUser(user);

      final RegistrationRequest signup = signupService.signup(user, productIds);
      val saved = userService.findByUsername(user.getUsername());
      saved.getDetails().setImage(iconService.iconDirect(saved.getUsername(), 64, 64));

      final RegistrationConfirmationElement registrationConfirmationElement =
          registrations.toConfirmation(signup);
      signup.getProducts();
      return registrationConfirmationElement;
    } catch (PersistenceException ex) {
      throw new DuplicateElementException("username and e-mail address must be unique");
    }
  }

  @Override
  public Response getOptions() {
    return Response.status(Response.Status.OK)
        .allow("POST")
        .header("Access-Control-Allow-Origin", "*")
        .header("Access-Control-Allow-Methods", "POST, GET, OPTIONS")
        .header("Access-Control-Max-Age", 1000)
        .header("Access-Control-Allow-Headers", "origin, x-csrftoken, content-type, accept")
        .build();
  }

  @Override
  @PreAuthorize("hasAuthority('admin')")
  public List<PrincipalElement> approvedUsers() {
    return entityManager
        .createQuery("select e from User e where e.active = true", User.class)
        .getResultList()
        .stream()
        .map(users::toElement)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
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

  @Override
  public RegistrationRequestElement delete(String id) {
    List<RegistrationRequest> requests =
        entityManager
            .createQuery(
                "select r from RegistrationRequest r where r.requestId = :id",
                RegistrationRequest.class)
            .setParameter("id", id)
            .getResultList();
    if (!requests.isEmpty()) {
      RegistrationRequest request = requests.get(0);
      entityManager.remove(request);
      entityManager.flush();
      return registrations.toElement(request);
    }
    throw new NoSuchElementException("No registration request with that id");
  }

  private void checkUser(User user) {
    if (user.getUsername() == null) {
      throw new IllegalArgumentException("Username must not be null");
    }
    if (user.getPassword() == null) {
      throw new IllegalArgumentException("Password must not be null");
    }
  }
}
