package io.sunshower.sdk.v1.security;

import io.sunshower.core.security.crypto.EncryptionService;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.test.SdkTestConfiguration;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.model.core.security.*;
import io.sunshower.test.ws.RESTTest;
import io.sunshower.test.ws.Remote;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;

@EnableAutoConfiguration(exclude = {
        SecurityAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        FlywayAutoConfiguration.class
})
public class DefaultSecurityEndpointTest extends SdkTest {

    @PersistenceContext
    private EntityManager entityManager;


    @Remote
    private SecurityEndpoint securityEndpoint;

    @Inject
    private EncryptionService encryptionService;

    @Test
    public void ensureSecurityEndpointIsInjected() {
        assertThat(securityEndpoint, is(not(nullValue())));
    }


    @Test
    public void ensureSecurityEndpointThrowsCorrectExceptionWhenProvidedNullToken() {
        assertThrows(IllegalStateException.class, () -> {
            securityEndpoint.authenticate((AuthenticationTokenElement) null);
        });
    }

    @Test
    public void ensureAuthenticateThrows405WhenProvidedNullTokenComponent() {
        AuthenticationTokenElement element = new AuthenticationTokenElement(null);
        assertThrows(NotAuthorizedException.class, () -> {
            securityEndpoint.authenticate(element);
        });
    }

    @Test
    public void ensureAuthenticatingWithInvalidUsernameProducesSaneException() {
        assertThrows(NotFoundException.class, () -> {
            Authenticate.as(null).withPassword("frapper").at(securityEndpoint);
        });
    }

    @Test
    public void ensureLoggingInAsExistingUserProducesToken() {
        AuthenticationElement result =
                Authenticate.as("administrator")
                .withPassword("frapadap")
                .at(securityEndpoint);
        assertThat(result, is(not(nullValue())));
        assertThat(result.getToken(), is(not(nullValue())));
        securityEndpoint.validate(result.getToken());
    }



    @Test
    public void ensureAuthenticatingViaTokenProducesPrincipalWithCorrectRoles() {

        AuthenticationElement result =
                Authenticate.as("administrator")
                        .withPassword("frapadap")
                        .at(securityEndpoint);

        final PrincipalElement principalElement = result.getPrincipal();

        assertThat(principalElement, is(not(nullValue())));
        assertThat(principalElement.getUsername(), is("administrator"));
        assertThat(principalElement.getRoles().size(), is(2));
        Set<String> roleNames = principalElement.getRoles()
                .stream().map(RoleElement::getAuthority).collect(Collectors.toSet());
        assertThat(roleNames, is(new HashSet<>(Arrays.asList("admin", "tenant:user"))));
    }



}