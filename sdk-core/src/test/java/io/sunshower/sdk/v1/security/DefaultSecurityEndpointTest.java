package io.sunshower.sdk.v1.security;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.sunshower.core.security.crypto.EncryptionService;
import io.sunshower.model.core.auth.Role;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.faults.authorization.AuthenticationFailedException;
import io.sunshower.sdk.v1.model.core.security.*;
import io.sunshower.service.security.PermissionsService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.acls.model.Permission;
import org.springframework.test.annotation.Rollback;

// @Sql(
//  executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
//  scripts = "classpath:/sql/drop-roles.sql"
// )
@Rollback
class DefaultSecurityEndpointTest extends SdkTest {

  @PersistenceContext private EntityManager entityManager;

  @Inject private SecurityEndpoint securityEndpoint;

  @Inject private SignupEndpoint endpoint;

  @Inject private EncryptionService encryptionService;
  @Inject private PermissionsService<Permission> permissionsService;

  @Test
  @BeforeEach
  public void ensureSecurityEndpointIsInjected() {
    assertThat(securityEndpoint, is(not(nullValue())));
  }

  @Test
  public void ensureSecurityEndpointThrowsCorrectExceptionWhenProvidedNullToken() {
    assertThrows(
        NullPointerException.class,
        () -> {
          securityEndpoint.authenticate((AuthenticationTokenElement) null);
        });
  }

  @Test
  public void ensureAuthenticateThrows405WhenProvidedNullTokenComponent() {
    AuthenticationTokenElement element = new AuthenticationTokenElement(null);
    assertThrows(
        AuthenticationFailedException.class,
        () -> {
          securityEndpoint.authenticate(element);
        });
  }

  @Test
  public void ensureAuthenticatingWithInvalidUsernameProducesSaneException() {
    assertThrows(
        NoResultException.class,
        () -> {
          Authenticate.as(null).withPassword("frapper").at(securityEndpoint);
        });
  }

  @Test
  public void ensureLoggingInAsExistingUserProducesToken() {
    register();
    AuthenticationElement result =
        Authenticate.as("hozesta").withPassword("whatever").at(securityEndpoint);
    assertThat(result, is(not(nullValue())));
    assertThat(result.getToken(), is(not(nullValue())));
    assertThat(result.getPrincipal().getImageElement(), is(not(nullValue())));
    securityEndpoint.validate(result.getToken());
  }

  @Test
  public void ensureAuthenticatingViaTokenProducesPrincipalWithCorrectRoles() {
    RegistrationConfirmationElement result = register();

    permissionsService.impersonate(
        () -> {
          final PrincipalElement principalElement = result.getPrincipal();

          assertThat(principalElement, is(not(nullValue())));
          assertThat(principalElement.getUsername(), is("hozesta"));
          assertThat(principalElement.getRoles().size(), is(1));
          Set<String> roleNames =
              principalElement
                  .getRoles()
                  .stream()
                  .map(RoleElement::getAuthority)
                  .collect(Collectors.toSet());
          assertThat(roleNames, is(new HashSet<>(Arrays.asList("tenant:user"))));
        },
        new Role("admin"));
  }

  private RegistrationConfirmationElement register() {
    val user =
        RegistrationRequestElement.newRegistration()
            .firstName("josiah")
            .password("whatever")
            .username("hozesta")
            .emailAddress("joe@whatever.com")
            .create();
    val result = endpoint.signup(user);
    permissionsService.impersonate(
        () -> endpoint.approve(result.getRegistrationId()), new Role("admin"));
    return result;
  }
}
