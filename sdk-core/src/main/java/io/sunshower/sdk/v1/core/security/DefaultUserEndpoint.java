package io.sunshower.sdk.v1.core.security;

import io.sunshower.core.security.UserService;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.core.model.Authentications;
import io.sunshower.sdk.v1.endpoints.core.security.UserEndpoint;
import io.sunshower.sdk.v1.model.core.Users;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultUserEndpoint implements UserEndpoint {

  @Inject private Users users;
  @Inject private UserService userService;

  @Override
  public List<PrincipalElement> list(boolean active) {
    List<User> results = active ? userService.activeUsers() : userService.inactiveUsers();
    return results.stream().map(users::toElement).collect(Collectors.toList());
  }
}
