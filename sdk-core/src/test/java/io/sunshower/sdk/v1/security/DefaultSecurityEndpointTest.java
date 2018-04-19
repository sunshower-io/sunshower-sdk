package io.sunshower.sdk.v1.security;

import io.sunshower.core.security.crypto.EncryptionService;
import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.test.TestRoles;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.security.*;
import io.sunshower.test.ws.Remote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql(
  executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
  scripts = "classpath:/sql/drop-roles.sql"
)
public class DefaultSecurityEndpointTest extends SdkTest {

  @PersistenceContext private EntityManager entityManager;

  @Remote private SecurityEndpoint securityEndpoint;

  @Remote private SignupEndpoint endpoint;

  @Inject private EncryptionService encryptionService;

  @Test
  @BeforeEach
  public void ensureSecurityEndpointIsInjected() {
    assertThat(securityEndpoint, is(not(nullValue())));
  }

  @Test
  public void ensureSecurityEndpointThrowsCorrectExceptionWhenProvidedNullToken() {
    assertThrows(
        BadRequestException.class,
        () -> {
          securityEndpoint.authenticate((AuthenticationTokenElement) null);
        });
  }

  @Test
  public void ensureAuthenticateThrows405WhenProvidedNullTokenComponent() {
    AuthenticationTokenElement element = new AuthenticationTokenElement(null);
    assertThrows(
        NotAuthorizedException.class,
        () -> {
          securityEndpoint.authenticate(element);
        });
  }

  @Test
  public void ensureAuthenticatingWithInvalidUsernameProducesSaneException() {
    assertThrows(
        NotFoundException.class,
        () -> {
          Authenticate.as(null).withPassword("frapper").at(securityEndpoint);
        });
  }

  @Test
  public void ensureLoggingInAsExistingUserProducesToken() {
    withPrincipals(TestRoles.administrator1())
        .perform(
            () -> {
              AuthenticationElement result =
                  Authenticate.as("administrator1").withPassword("frapadap1").at(securityEndpoint);
              assertThat(result, is(not(nullValue())));
              assertThat(result.getToken(), is(not(nullValue())));
              securityEndpoint.validate(result.getToken());
            });
  }

  @Test
  public void ensureAuthenticatingViaTokenProducesPrincipalWithCorrectRoles() {
    withPrincipals(TestRoles.administrator1())
        .perform(
            () -> {
              AuthenticationElement result =
                  Authenticate.as("administrator1").withPassword("frapadap1").at(securityEndpoint);

              final PrincipalElement principalElement = result.getPrincipal();

              assertThat(principalElement, is(not(nullValue())));
              assertThat(principalElement.getUsername(), is("administrator1"));
              assertThat(principalElement.getRoles().size(), is(3));
              Set<String> roleNames =
                  principalElement
                      .getRoles()
                      .stream()
                      .map(RoleElement::getAuthority)
                      .collect(Collectors.toSet());
              assertThat(
                  roleNames,
                  is(new HashSet<>(Arrays.asList("admin", "tenant:user", "tenant:admin"))));
            });
  }
}
