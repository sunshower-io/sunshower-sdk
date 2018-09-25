package io.sunshower.sdk.v1.model.core.security;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import org.junit.jupiter.api.Test;

class PrincipalElementTest extends SerializationTestCase {

  public PrincipalElementTest() {
    super(SerializationAware.Format.JSON, new Class<?>[] {PrincipalElement.class});
  }

  @Test
  void ensureElementIdIsWritten() {
    final PrincipalElement el = new PrincipalElement();
    PrincipalElement copy = copy(el);
    assertThat(copy.getId(), is(el.getId()));
  }

  @Test
  public void ensureRolesAreWritten() {

    final PrincipalElement el = new PrincipalElement();
    final RoleElement role = new RoleElement();
    el.addRole(role);
    el.setEmailAddress("joe@whatever.com");
    PrincipalElement copy = copy(el);
    assertThat(copy.getEmailAddress(), is(el.getEmailAddress()));
    assertThat(copy.getRoles().size(), is(1));
  }

  @Test
  public void ensurePrincipalElementTypeIsCorrect() {
    final PrincipalElement el = new PrincipalElement();
    write(el, System.out);
  }

  @Test
  public void ensureEmailAddressIsWritten() {

    final PrincipalElement el = new PrincipalElement();
    el.setEmailAddress("joe@whatever.com");
    PrincipalElement copy = copy(el);
    assertThat(copy.getEmailAddress(), is(el.getEmailAddress()));
  }
}
