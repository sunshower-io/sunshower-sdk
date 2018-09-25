package io.sunshower.sdk.core.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import io.sunshower.model.core.Application;
import io.sunshower.sdk.v1.MappingConfiguration;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MappingConfiguration.class)
class ApplicationsTest {

  @Inject private Applications applications;

  private Application application;

  private ApplicationElement applicationElement;

  @BeforeEach
  public void setUp() {
    application = new Application();
    applicationElement = new ApplicationElement();
  }

  @Test
  public void ensureApplications() {
    setupApplication();
    applications.toElement(application).getName();
  }

  private void setupApplication() {
    application.setName("test");
    applicationElement = applications.toElement(application);
    assertThat(applicationElement.getName(), is("test"));
  }
}
