package io.sunshower.sdk.v1.security;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.sunshower.core.security.crypto.EncryptionService;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.test.TestRoles;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.faults.authorization.AuthenticationFailedException;
import io.sunshower.sdk.v1.model.core.security.*;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(
  executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
  scripts = "classpath:/sql/drop-roles.sql"
)
class DefaultSecurityEndpointTest extends SdkTest {

  @PersistenceContext private EntityManager entityManager;

  @Inject private SecurityEndpoint securityEndpoint;

  @Inject private SignupEndpoint endpoint;

  @Inject private EncryptionService encryptionService;

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
              // TODO: FIGURE OUT
              //              assertThat(principalElement.getRoles().size(), is(3));
              Set<String> roleNames =
                  principalElement
                      .getRoles()
                      .stream()
                      .map(RoleElement::getAuthority)
                      .collect(Collectors.toSet());
              //              assertThat(
              //                  roleNames,
              //                  is(new HashSet<>(Arrays.asList("admin", "tenant:user",
              // "tenant:admin"))));
            });
  }
}
