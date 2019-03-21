package io.sunshower.sdk.v1.model.core;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.MappingConfiguration;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
  void ensureFirstNameIsCopiedCorrectly() {
    user.getDetails().setFirstName("blammut");
    principalElement = users.completeElement(user);
    assertThat(principalElement.getFirstName(), is("blammut"));
  }

  @Test
  void ensureDetailsIdIsCopiedCorrectly() {
    principalElement = users.completeElement(user);
    assertThat(principalElement.getDetailsId(), is(user.getDetails().getId()));
  }

  @Test
  public void ensureEmailAddressIsCopiedCorrectly() {

    principalElement = users.toElement(user);
    assertThat(principalElement.getEmailAddress(), is("frap@dap.com"));
    assertThat(principalElement.getPassword(), is(nullValue()));
  }

  @Test
  public void ensurePasswordIsCopiedFromElementToUser() {
    principalElement.setPassword("beanasaur");
    user = users.toModel(principalElement);
    assertThat(user.getPassword(), is("beanasaur"));
  }

  @Test
  public void ensureUserNameIsMappedCorrectlyFromModelToElement() {
    principalElement = users.toElement(user);
    assertThat(principalElement.getRoles().size(), is(1));
    assertThat(principalElement.getRoles().get(0).getAuthority(), is("admin"));
    assertThat(
        principalElement.getRoles().get(0).getId(), is(user.getRoles().iterator().next().getId()));
  }
}
