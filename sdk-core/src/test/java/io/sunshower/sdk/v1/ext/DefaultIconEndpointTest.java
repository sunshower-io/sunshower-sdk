package io.sunshower.sdk.v1.ext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import io.sunshower.common.Identifier;
import io.sunshower.model.core.auth.Details;
import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.endpoints.ext.IconEndpoint;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import io.sunshower.sdk.v1.model.ext.ChangeIconRequest;
import io.sunshower.sdk.v1.model.ext.ImageElement;
import io.sunshower.service.ext.IconService;
import io.sunshower.service.security.PermissionsService;
import java.util.Base64;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.security.acls.model.Permission;

class DefaultIconEndpointTest extends SdkTest {
  @Inject private IconService iconService;
  @Inject private IconEndpoint iconEndpoint;
  @Inject private SignupEndpoint signupEndpoint;
  @Inject private SecurityEndpoint securityEndpoint;
  @Inject private PermissionsService<Permission> permissionsService;

  @PersistenceContext private EntityManager entityManager;

  @Test
  void ensureGettingImageFromSignedUpUserWorks() {
    val id = defaultScenario();
    val details = entityManager.find(User.class, id).getDetails().getId();

    permissionsService.impersonate(
        () -> {
          val icon = iconEndpoint.getIcon(Details.class, details);
          assertThat(icon.getFormat(), is("SVG"));
          val gumwab = iconService.iconDirect("gumwab", 64, 64);
          assertThat(Base64.getDecoder().decode(icon.getData()), is(gumwab.getData()));
        },
        "gumwab");
  }

  @Test
  void ensureChangingImageWorks() {
    val id = defaultScenario();
    val details = entityManager.find(User.class, id).getDetails().getId();

    permissionsService.impersonate(
        () -> {
          val icon = iconEndpoint.getIcon(Details.class, details);
          assertThat(icon.getFormat(), is("SVG"));
          val gumwab = iconService.iconDirect("frapper", 64, 64);
          val req = new ChangeIconRequest();
          val imgel = new ImageElement();
          imgel.setData(Base64.getEncoder().encodeToString(gumwab.getData()));
          imgel.setFormat("SVG");
          req.setTargetType(Details.class);
          req.setImageElement(imgel);
          iconEndpoint.setIcon(details, req);
          val data = iconEndpoint.getIcon(Details.class, details).getData();
          assertThat(Base64.getDecoder().decode(data), is(gumwab.getData()));
        },
        "gumwab");
  }

  private Identifier defaultScenario() {
    RegistrationRequestElement registrationRequestElement =
        RegistrationRequestElement.newRegistration()
            .firstName("test")
            .lastName("whatever")
            .locale(Locale.CANADA)
            .username("gumwab")
            .phoneNumber("970-888-8888")
            .emailAddress("gumasaur@gmail.com")
            .password("frapper")
            .create();

    val resp = signupEndpoint.signup(registrationRequestElement);
    AtomicReference<Identifier> id = new AtomicReference<>();
    permissionsService.impersonate(
        () -> {
          val approve = signupEndpoint.approve(resp.getRegistrationId());
          id.set(approve.getValue());
        },
        new Role("admin"));
    return id.get();
  }
}
