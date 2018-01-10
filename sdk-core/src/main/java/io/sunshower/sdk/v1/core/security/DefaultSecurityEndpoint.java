package io.sunshower.sdk.v1.core.security;

import io.sunshower.core.security.AuthenticationService;
import io.sunshower.core.security.InvalidCredentialException;
import io.sunshower.model.core.auth.Authentication;
import io.sunshower.model.core.auth.Token;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.core.model.Authentications;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.model.core.faults.authorization.AuthenticationFailedException;
import io.sunshower.sdk.v1.model.core.security.AuthenticationElement;
import io.sunshower.sdk.v1.model.core.security.AuthenticationRequest;
import io.sunshower.sdk.v1.model.core.security.AuthenticationTokenElement;

import javax.inject.Inject;
import java.util.Date;

public class DefaultSecurityEndpoint implements SecurityEndpoint {

  @Inject private Authentications authentications;

  @Inject private AuthenticationService authenticationService;

  @Override
  public void validate(AuthenticationTokenElement element) {
    authenticationService.validate(new Token(element.getValue(), new Date()));
  }

  @Override
  public AuthenticationElement authenticate(AuthenticationRequest request) {
    final User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    Authentication authenticate = authenticationService.authenticate(user);
    return authentications.toElement(authenticate);
  }

  @Override
  public AuthenticationElement authenticate(AuthenticationTokenElement token) {
    final String value = token.getValue();
    if (value == null || value.isEmpty()) {
      throw new AuthenticationFailedException();
    }
    Authentication validate =
        authenticationService.validate(new Token(token.getValue(), new Date()));
    return authentications.toElement(validate);
  }
}
