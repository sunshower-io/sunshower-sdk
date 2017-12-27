package io.sunshower.sdk.v1.core.security;

import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.v1.endpoints.core.security.UserEndpoint;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.test.persist.Authority;
import io.sunshower.test.persist.Principal;
import io.sunshower.test.ws.Remote;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by haswell on 5/11/17.
 */

@EnableAutoConfiguration(exclude = {
        SecurityAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        FlywayAutoConfiguration.class
})
public class DefaultUserEndpointTest extends SdkTest {

    @Remote
    private UserEndpoint userEndpoint;


    @PersistenceContext
    private EntityManager entityManager;
    
    


    @Principal
    public User tenantUser(@Authority("tenant:user") Role role) {
        final User tenantUser = new User();
        tenantUser.setActive(true);
        tenantUser.setUsername("tenantUser");
        tenantUser.addRole(role);
        tenantUser.setPassword(password("tenantUser", "password"));
        tenantUser.getDetails().setEmailAddress("frap@dap.com");
        return tenantUser;
    }

    @Test
    public void ensureListingActiveUsersReturnsEmptyListWhenNoUsersExist() {
        changeSession("administrator");
        assertThat(userEndpoint.list(true).size(), is(3));
    }


    @Test
    public void ensureListingActiveUsersWhileUnauthorizedProduces401() {
        assertThrows(NotAuthorizedException.class, () -> {
            assertThat(userEndpoint.list(true).size(), is(2));
        });
    }

    @Test
    public void ensureListingActiveUsersFailsWithUnauthorizedWhenAuthenticatedAsTenantUser() {
        changeSession("tenantUser");
        assertThrows(ForbiddenException.class, () -> {
            assertThat(userEndpoint.list(true).size(), is(2));
        });
    }

    @Test
    public void ensureNoInactiveUsersAppearInActiveUserList() {
        changeSession("administrator");
        final User inactiveUser = createInactiveUser();
        entityManager.persist(inactiveUser);
        entityManager.flush();
        List<PrincipalElement> list = userEndpoint.list(true);
        System.out.println(list);
        assertThat(userEndpoint.list(true).stream()
                .allMatch(PrincipalElement::isActive), is(true));
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
        changeSession("administrator");
        final PrincipalElement principal = userEndpoint.list(true).get(0);
        assertThat(principal.getEmailAddress(), is(not(nullValue())));
    }

    @Test
    public void ensureListingInactiveUsersReturnsEmptyListWhenNoUsersExist() {
        changeSession("administrator");
        assertThat(userEndpoint.list(false).size(), is(0));
    }


    @Test
    public void ensureListingInactiveUsersWhileUnauthorizedProduces401() {
        assertThrows(NotAuthorizedException.class, () -> {
            assertThat(userEndpoint.list(false).size(), is(0));
        });
    }

    @Test
    public void ensureListingInactiveUsersFailsWithUnauthorizedWhenAuthenticatedAsTenantUser() {
        changeSession("tenantUser");
        assertThrows(ForbiddenException.class, () -> {
            assertThat(userEndpoint.list(false).size(), is(0));
        });
    }

    @Test
    public void ensureNoActiveUsersAppearInInactiveUserList() {
        final User inactiveUser = createInactiveUser();
        changeSession("administrator");
        entityManager.persist(inactiveUser);
        entityManager.flush();
        assertThat(userEndpoint.list(false).stream()
                .allMatch(PrincipalElement::isActive), is(false));
    }

    @Test
    public void ensureInactiveUserReturnsWithRelevantDetails() {
        changeSession("administrator");
        final User inactiveUser = createInactiveUser();
        entityManager.persist(inactiveUser);
        entityManager.flush();
        final PrincipalElement principal = userEndpoint.list(false).get(0);
        assertThat(principal.getEmailAddress(), is(not(nullValue())));
    }


}