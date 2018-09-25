package io.sunshower.sdk.core.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import io.sunshower.common.Identifier;
import io.sunshower.model.core.auth.Activation;
import io.sunshower.sdk.v1.MappingConfiguration;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import java.util.Date;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MappingConfiguration.class)
class ActivationsTest {

  @Inject Activations activations;

  @Test
  public void ensureNoExceptionsAreThrown() {
    activations.toElement(new Activation());
  }

  @Test
  public void ensureIdIsMappedCorrectly() {
    Activation activation = new Activation();
    assertThat(activations.toElement(activation).getId(), is(activation.getId()));
  }

  @Test
  public void ensureIdInverseIsMappedCorrectly() {
    ActivationElement activation = new ActivationElement();
    activation.setId(Identifier.random());
    assertThat(activations.toModel(activation).getId(), is(activation.getId()));
  }

  @Test
  public void ensureDateIsMappedCorrectly() {
    ActivationElement activation = new ActivationElement();
    activation.setActivationDate(new Date());
    Activation model = activations.toModel(activation);
    assertThat(model.getActivationDate(), is(activation.getActivationDate()));
  }

  @Test
  public void ensureUserIsMappedCorrectly() {
    ActivationElement e = new ActivationElement();
    e.setId(Identifier.random());
    PrincipalElement p = new PrincipalElement();
    p.setActive(true);
    p.setUsername("joe");
    p.setFirstName("firstname");
    e.setActivator(p);
    Activation model = activations.toModel(e);
    assertThat(model.getActivator(), is(not(nullValue())));
    assertThat(model.getActivator().getUsername(), is(p.getUsername()));
  }
}
