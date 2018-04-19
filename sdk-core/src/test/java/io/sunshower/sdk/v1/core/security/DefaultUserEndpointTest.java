package io.sunshower.sdk.v1.core.security;

import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.core.ActivationEndpoint;
import io.sunshower.sdk.lang.IdentifierElement;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.UserEndpoint;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import io.sunshower.service.security.PermissionsService;
import io.sunshower.test.ws.Remote;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;

import static io.sunshower.sdk.test.TestRoles.administrator;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultUserEndpointTest extends SdkTest {

  @Inject private PermissionsService<?> permissionsService;

  @Remote private UserEndpoint userEndpoint;
  @Remote private SignupEndpoint signupEndpoint;
  @Remote private ActivationEndpoint activationEndpoint;

  @PersistenceContext private EntityManager entityManager;

  @Test
  public void ensureListingActiveUsersReturnsEmptyListWhenNoUsersExist() {
    withPrincipals(administrator())
        .perform(
            () -> {
              changeSession("administrator");
              assertTrue(userEndpoint.list(true).size() > 0);
            });
  }

  IdentifierElement id;

  @Test
  void ensureSavingAndDeletingUserWorks() {
    try {
      RegistrationRequestElement registrationRequestElement =
          RegistrationRequestElement.newRegistration()
              .firstName("wab")
              .lastName("dab")
              .username("wabbab")
              .phoneNumber("970-888-8888")
              .emailAddress("coolbeanszzes")
              .password("coolbeans")
              .create();
      signupEndpoint.signup(registrationRequestElement);
      permissionsService.impersonate(
          () -> {
            List<RegistrationRequestElement> list = signupEndpoint.list();
            assertTrue(signupEndpoint.list().stream().anyMatch(t -> "wabbab".equals(t.getUsername())));
            id = signupEndpoint.approve(list.get(0).getRegistrationId());
          },
          new Role("admin"));
    } finally {
      if (id != null) {
        permissionsService.impersonate(() -> userEndpoint.delete(id.getValue()), new Role("admin"));
      }
    }
  }

  @Test
  public void ensureListingActiveUsersWhileUnauthorizedProduces401() {
    assertThrows(
        NotAuthorizedException.class,
        () -> {
          assertThat(userEndpoint.list(true).size(), is(2));
        });
  }

  @Test
  public void ensureListingActiveUsersFailsWithUnauthorizedWhenAuthenticatedAsTenantUser() {
    assertThrows(
        ForbiddenException.class,
        () -> {
          permissionsService.impersonate(
              () -> {
                assertThat(userEndpoint.list(true).size(), is(2));
              },
              new Role("tenant:user"));
        });
  }

  @Test
  public void ensureNoInactiveUsersAppearInActiveUserList() {
    withPrincipals(administrator())
        .perform(
            () -> {
              changeSession("administrator");
              final User inactiveUser = createInactiveUser();
              entityManager.persist(inactiveUser);
              entityManager.flush();
              List<PrincipalElement> list = userEndpoint.list(true);
              System.out.println(list);
              assertThat(
                  userEndpoint.list(true).stream().allMatch(PrincipalElement::isActive), is(true));
            });
  }

  @NotNull
  private User createInactiveUser() {
    final User inactiveUser = new User();
    inactiveUser.setActive(false);
    inactiveUser.setUsername("inactiveBlorper");
    inactiveUser.setPassword(password("inactiveBlorper", "password"));
    inactiveUser.getDetails().setEmailAddress("inactive@blorper.com");
    return inactiveUser;
  }

  @Test
  public void ensureActiveUserReturnsWithRelevantDetails() {
    withPrincipals(administrator())
        .perform(
            () -> {
              changeSession("administrator");
              final PrincipalElement principal = userEndpoint.list(true).get(0);
              assertThat(principal.getEmailAddress(), is(not(nullValue())));
            });
  }

  @Test
  public void ensureListingInactiveUsersReturnsEmptyListWhenNoUsersExist() {
    withPrincipals(administrator())
        .perform(
            () -> {
              changeSession("administrator");
              assertTrue(userEndpoint.list(false).stream().noneMatch(PrincipalElement::isActive));
            });
  }

  @Test
  public void ensureListingInactiveUsersWhileUnauthorizedProduces401() {
    assertThrows(
        NotAuthorizedException.class,
        () -> {
          assertThat(userEndpoint.list(false).size(), is(0));
        });
  }

  @Test
  public void ensureListingInactiveUsersFailsWithUnauthorizedWhenAuthenticatedAsTenantUser() {
    assertThrows(
        ForbiddenException.class,
        () -> {
          permissionsService.impersonate(
              () -> {
                assertThat(userEndpoint.list(false).size(), is(0));
              },
              new Role("tenant:user"));
        });
  }
}
