package io.sunshower.sdk.v1.core.security;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import io.sunshower.model.core.auth.Authentication;
import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.Token;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.core.model.Authentications;
import io.sunshower.sdk.v1.MappingConfiguration;
import io.sunshower.sdk.v1.model.core.security.AuthenticationElement;
import io.sunshower.sdk.v1.model.core.security.RoleElement;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MappingConfiguration.class)
public class AuthenticationsTest {

  @Inject private Authentications authentications;

  private User user;
  private Token token;
  private Authentication authentication;
  private AuthenticationElement authenticationElement;

  @BeforeEach
  public void setUp() {
    token = new Token("coolbeans", new Date());
    user = new User();
    authentication = new Authentication();
  }

  @Test
  public void ensurePrincipalIsCopiedFromAuthentication() {
    authentication.setUser(user);
    authenticationElement = authentications.toElement(authentication);
    assertThat(authenticationElement.getPrincipal(), is(not(nullValue())));
  }

  @Test
  public void ensureTokenIsCopiedOverFromAuthentication() {
    authentication.setToken(token);
    authenticationElement = authentications.toElement(authentication);
    assertThat(authenticationElement.getToken(), is(not(nullValue())));
    assertThat(authenticationElement.getToken().getValue(), is("coolbeans"));
  }

  @Test
  public void ensurePhoneNumberIsCopiedOverInFromModel() {
    //        final User blorper = new User();
    //        blorper.getDetails().setPhoneNumber("123");
    //        assertThat(fromModel(blorper).getPhoneNumber(), is("123"));
  }

  @Test
  public void ensureMappingOverHierarchyProducesExpectedResults() {

    final Role root = new Role("mom");
    final Role brother = new Role("kai");
    final Role sister = new Role("julie");
    final Role wife = new Role("wabbus");

    final Role bee = new Role("bee");
    final Role addy = new Role("addy");
    wife.addChild(bee);
    wife.addChild(addy);

    root.addChild(brother);
    root.addChild(sister);
    root.addChild(wife);

    List<RoleElement> roleElement = Authentications.mapRoles(root);
    assertThat(roleElement.size(), is(6));

    assertThat(
        roleElement.stream().map(RoleElement::getAuthority).collect(Collectors.toSet()),
        is(new HashSet<>(Arrays.asList("mom", "kai", "julie", "wabbus", "bee", "addy"))));
  }
}
