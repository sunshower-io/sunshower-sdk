package io.sunshower.sdk.v1.model.core;

import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.MappingConfiguration;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import javax.inject.Inject;

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MappingConfiguration.class)
class UsersTest {

  @Inject private Users users;

  User user;
  PrincipalElement principalElement;

  @BeforeEach
  public void setUp() {
    user = new User();
    user.setActive(true);
    user.setUsername("username");
    user.setPassword("password");
    user.getDetails().setEmailAddress("frap@dap.com");
    Role admin = new Role();
    admin.setAuthority("admin");
    user.addRole(admin);

    principalElement = new PrincipalElement();
    principalElement.setFirstName("user2");
    principalElement.setLastName("userln2");
    principalElement.setActive(true);
    principalElement.setEmailAddress("joe@joe.com");
  }
  
  @Test
  public void ensureEmailAddressIsCopiedCorrectly() {

      principalElement = users.toElement(user);
      assertThat(principalElement.getEmailAddress(), is("frap@dap.com"));
      
  }

  @Test
  public void ensureUserNameIsMappedCorrectlyFromModelToElement() {
      principalElement = users.toElement(user);
      assertThat(principalElement.getRoles().size(), is(1));
      assertThat(principalElement.getRoles().get(0).getAuthority(), is("admin"));
      assertThat(principalElement.getRoles().get(0).getId(), is(user.getRoles().iterator().next().getId()));
  }
}
