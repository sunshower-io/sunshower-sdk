package io.sunshower.sdk.test;

import io.sunshower.core.security.UserService;
import io.sunshower.core.security.crypto.EncryptionService;
import io.sunshower.jpa.flyway.FlywayConfiguration;
import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.User;
import io.sunshower.persist.core.DataSourceConfiguration;
import io.sunshower.persist.hibernate.HibernateConfiguration;
import io.sunshower.sdk.core.ActivationEndpoint;
import io.sunshower.sdk.core.model.ActivationElement;
import io.sunshower.sdk.v1.SdkConfiguration;
import io.sunshower.sdk.v1.model.core.element.PersistentElement;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.security.api.SecurityPersistenceConfiguration;
import io.sunshower.service.CoreServiceConfiguration;
import io.sunshower.service.security.Action;
import io.sunshower.service.security.SecurityConfiguration;
import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import io.sunshower.test.common.TestConfigurationConfiguration;
import io.sunshower.test.persist.AuthenticationTestExecutionListener;
import io.sunshower.test.persist.Authority;
import io.sunshower.test.persist.Principal;
import io.sunshower.test.ws.EnableJAXRS;
import io.sunshower.test.ws.Remote;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Rollback
@EnableJAXRS
@ExtendWith(SpringExtension.class)
@RunWith(JUnitPlatform.class)
@TestExecutionListeners(
        listeners = {
                ServletTestExecutionListener.class,
                DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionalTestExecutionListener.class,
//                AuthenticationTestExecutionListener.class,,

                WithSecurityContextTestExecutionListener.class
        },
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@ContextConfiguration(classes = {
        TestConfigurationConfiguration.class,
        SecurityPersistenceConfiguration.class,
        SdkConfiguration.class,
        CoreServiceConfiguration.class,
        SecurityConfiguration.class,
        FlywayConfiguration.class,
        DataSourceConfiguration.class,
        HibernateConfiguration.class,
        SdkTestConfiguration.class,
})
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
public abstract class SdkTest extends SerializationTestCase {

    static final Map<String, String> passwords = new ConcurrentHashMap<>();
    
    @Remote
    private ActivationEndpoint activationEndpoint; 

    static {
        TestSecurityContextHolder.initialize();
    }
    
    
    


    public SdkTest() {
        this(SerializationAware.Format.JSON, new Class[]{
                PersistentElement.class
        });
    }
    
    public SdkTest(SerializationAware.Format format, Class[] bound) {
        super(format, bound);
    }

    public SdkTest(SerializationAware.Format format, boolean includeRoot, Class[] bound) {
        super(format, includeRoot, bound);
    }

    @BeforeEach
    public void setUp() {
        TestSecurityContextHolder.clear();
    }

    @AfterEach
    public void tearDown() {
        TestSecurityContextHolder.clear();
    }


    @Inject
    protected UserService userService;

    @Inject
    protected EncryptionService encryptionService;


    @Authority("admin")
    public Role createAdminAuthority() {
        return new Role("admin");
    }

    @Authority("tenant:user")
    public Role createTenantRole() {
        return new Role("tenant:user");
    }

    @Principal
    @SuppressWarnings("all")
    public User createPrincipal(
            @Authority("admin") Role role,
            @Authority("tenant:user") Role user
    ) {
        final User administrator = new User();
        administrator.setUsername("administrator");
        administrator.setPassword(password("administrator", "frapadap"));
        administrator.setActive(true);
        role.addChild(user);
        administrator.addRole(role);
        administrator.getDetails().setEmailAddress("administrator");
        return administrator;
    }

    @Principal
    public User noRoles() {
        final User noRoles = new User();
        noRoles.setUsername("no-roles");
        noRoles.setPassword(password("no-roles", "frapadap"));
        noRoles.getDetails().setEmailAddress("dfasdfasdf");
        noRoles.setActive(true);
        return noRoles;
    }
    
    protected TestAction withPrincipals(PrincipalElement... principles) {
        final PrincipalAction action = new PrincipalAction(activationEndpoint);
        for(PrincipalElement principal : principles) {
            try {
                ActivationElement activate = activationEndpoint.activate(principal);
                action.addPrincipal(activate, principal.getPassword());
            } catch(Exception ex) {
                
            }
        }
        return action;
    }

    protected synchronized void changeSession(String username) {
        User u = userService.findByUsername(username);
        changeSession(username, passwords.get(username));
    }


    protected synchronized static void changeSession(String userId, String unencryptedPassword) {
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                userId, unencryptedPassword)
                );
    }

    protected String password(String username, String password) {
        passwords.put(username, password);
        return encryptionService.encrypt(password);
    }

    @AfterAll
    public static void tearDownClass() {
        passwords.clear();
    }
}
