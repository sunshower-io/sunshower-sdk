package io.sunshower.sdk.v1.core.security;

import io.sunshower.core.security.AuthenticationService;
import io.sunshower.model.core.auth.Authentication;
import io.sunshower.model.core.auth.Details;
import io.sunshower.model.core.auth.Token;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.core.model.Authentications;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.model.core.Registrations;
import io.sunshower.sdk.v1.model.core.faults.authorization.AuthenticationFailedException;
import io.sunshower.sdk.v1.model.core.security.AuthenticationElement;
import io.sunshower.sdk.v1.model.core.security.AuthenticationRequest;
import io.sunshower.sdk.v1.model.core.security.AuthenticationTokenElement;
import io.sunshower.service.ext.IconService;
import java.util.Date;
import javax.inject.Inject;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSecurityEndpoint implements SecurityEndpoint {

  @Inject private IconService iconService;
  @Inject private Registrations registrations;
  @Inject private Authentications authentications;
  @Inject private AuthenticationService authenticationService;

  @Override
  public void validate(AuthenticationTokenElement element) {
    authenticationService.validate(new Token(element.getValue(), new Date()));
  }

  @Override
  @Transactional(readOnly = true)
  public AuthenticationElement authenticate(AuthenticationRequest request) {
    val user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    Authentication authenticate = authenticationService.authenticate(user);
    val result = authentications.toElement(authenticate);
    val imageElement =
        iconService.getIcon(Details.class, authenticate.getUser().getDetails().getId(), true);
    result.getPrincipal().setImage(registrations.imageElement(imageElement));
    return result;
  }

  @Override
  @Transactional(readOnly = true)
  public AuthenticationElement authenticate(AuthenticationTokenElement token) {
    final String value = token.getValue();
    if (value == null || value.isEmpty()) {
      throw new AuthenticationFailedException();
    }
    Authentication validate =
        authenticationService.validate(new Token(token.getValue(), new Date()));
    val result = authentications.toElement(validate);
    val imageElement =
        iconService.getIcon(Details.class, validate.getUser().getDetails().getId(), true);
    result.getPrincipal().setImage(registrations.imageElement(imageElement));
    return result;
  }
}
