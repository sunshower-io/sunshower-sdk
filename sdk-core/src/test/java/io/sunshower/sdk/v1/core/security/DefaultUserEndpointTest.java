package io.sunshower.sdk.v1.core.security;

import static io.sunshower.sdk.test.TestRoles.administrator;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.sunshower.common.Identifier;
import io.sunshower.core.security.InvalidCredentialException;
import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.lang.IdentifierElement;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.UserEndpoint;
import io.sunshower.sdk.v1.model.core.security.*;
import io.sunshower.service.security.PermissionsService;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class DefaultUserEndpointTest extends SdkTest {

  @Inject private PermissionsService<?> permissionsService;

  @Inject private UserEndpoint userEndpoint;
  @Inject private SignupEndpoint signupEndpoint;
  @Inject private SecurityEndpoint securityEndpoint;
  @PersistenceContext private EntityManager entityManager;

  IdentifierElement id;

  @Test
  public void ensureListingActiveUsersReturnsEmptyListWhenNoUsersExist() {
    withPrincipals(administrator())
        .perform(
            () -> {
              changeSession("administrator");
              assertTrue(userEndpoint.list(true).size() > 0);
            });
  }

  @Test
  void ensureListingLocalesWorks() {
    assertThat(userEndpoint.getLocales().size(), is(Locale.getAvailableLocales().length));
  }

  @Test
  void ensureListingLocalesInLanguageWorks() {
    assertThat(userEndpoint.getLocales().size(), is(Locale.getAvailableLocales().length));
  }

  @Test
  void ensureUserCanBeRetrievedByThemselves() {
    Identifier id = defaultScenario();

    permissionsService.impersonate(
        () -> {
          val gumwab = userEndpoint.get(id);
          assertThat(gumwab.getUsername(), is("gumwab"));
        },
        "gumwab");
  }

  @Test
  void expectDefaults() {

    Identifier id = defaultScenario();

    val principalEl = new PrincipalElement();
    permissionsService.impersonate(
        () -> {
          val user = userEndpoint.get(id);
          assertThat(user.getFirstName(), is("josiah"));
          assertThat(user.getLastName(), is("gumerman"));
          assertThat(user.getUsername(), is("gumwab"));
          assertThat(user.getEmailAddress(), is("gumasaur@gmail.com"));
          assertThat(user.getLocale(), is(Locale.CANADA));
          assertThat(user.getPhoneNumber(), is("970-888-8888"));
        },
        "gumwab");
  }

  @Test
  void ensureUserCanUpdateTheirFirstName() {
    Identifier id = defaultScenario();

    val principalEl = new PrincipalElement();
    permissionsService.impersonate(
        () -> {
          principalEl.setFirstName("josiah2");
          userEndpoint.update(id, principalEl);
          assertThat(userEndpoint.get(id).getFirstName(), is("josiah2"));
        },
        "gumwab");
  }

  @Test
  void ensureUserCanUpdateTheirPhoneNumber() {

    Identifier id = defaultScenario();
    val principalEl = new PrincipalElement();
    permissionsService.impersonate(
        () -> {
          principalEl.setPhoneNumber("888-888-8888");
          userEndpoint.update(id, principalEl);
          assertThat(userEndpoint.get(id).getPhoneNumber(), is("888-888-8888"));
        },
        "gumwab");
  }

  @Test
  void ensureUserCanUpdateTheirPasswordAndSignIn() {
    Identifier id = defaultScenario();
    val principalEl = new PrincipalElement();
    permissionsService.impersonate(
        () -> {
          principalEl.setPassword("frapper");
          userEndpoint.update(id, principalEl);
        },
        "gumwab");

    Authenticate.as("gumwab").withPassword("frapper").at(securityEndpoint);
    assertThrows(
        InvalidCredentialException.class,
        () -> Authenticate.as("gumwab").withPassword("dapper").at(securityEndpoint));
  }

  @Test
  void ensureUserCanUpdateTheirLocale() {

    Identifier id = defaultScenario();
    val principalEl = new PrincipalElement();
    permissionsService.impersonate(
        () -> {
          principalEl.setLocale(Locale.CHINA);
          userEndpoint.update(id, principalEl);
          assertThat(userEndpoint.get(id).getLocale(), is(Locale.CHINA));
        },
        "gumwab");
  }

  @Test
  void ensureUserCanUpdateTheirLastName() {

    Identifier id = defaultScenario();

    val principalEl = new PrincipalElement();
    permissionsService.impersonate(
        () -> {
          principalEl.setLastName("coolbeans");
          userEndpoint.update(id, principalEl);
          assertThat(userEndpoint.get(id).getLastName(), is("coolbeans"));
        },
        "gumwab");
  }

  @Test
  void ensureRetrievingDetailsWorks() {
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
      val resp = signupEndpoint.signup(registrationRequestElement);
      permissionsService.impersonate(
          () -> {
            userEndpoint.get(resp.getPrincipal().getId());
          },
          new Role("admin"));
    } finally {
      if (id != null) {
        permissionsService.impersonate(() -> userEndpoint.delete(id.getValue()), new Role("admin"));
      }
    }
  }

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
            assertTrue(
                signupEndpoint.list().stream().anyMatch(t -> "wabbab".equals(t.getUsername())));
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
        AuthenticationCredentialsNotFoundException.class,
        () -> {
          assertThat(userEndpoint.list(true).size(), is(2));
        });
  }

  @Test
  public void ensureListingActiveUsersFailsWithUnauthorizedWhenAuthenticatedAsTenantUser() {
    assertThrows(
        AccessDeniedException.class,
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
        AuthenticationCredentialsNotFoundException.class,
        () -> {
          assertThat(userEndpoint.list(false).size(), is(0));
        });
  }

  @Test
  public void ensureListingInactiveUsersFailsWithUnauthorizedWhenAuthenticatedAsTenantUser() {
    assertThrows(
        AccessDeniedException.class,
        () -> {
          permissionsService.impersonate(
              () -> {
                assertThat(userEndpoint.list(false).size(), is(0));
              },
              new Role("tenant:user"));
        });
  }

  private Identifier defaultScenario() {
    RegistrationRequestElement registrationRequestElement =
        RegistrationRequestElement.newRegistration()
            .firstName("josiah")
            .lastName("gumerman")
            .locale(Locale.CANADA)
            .username("gumwab")
            .phoneNumber("970-888-8888")
            .emailAddress("gumasaur@gmail.com")
            .password("frapper")
            .create();

    val resp = signupEndpoint.signup(registrationRequestElement);
    AtomicReference<Identifier> id = new AtomicReference<>();
    permissionsService.impersonate(
        () -> {
          val approve = signupEndpoint.approve(resp.getRegistrationId());
          id.set(approve.getValue());
        },
        new Role("admin"));
    return id.get();
  }
}
