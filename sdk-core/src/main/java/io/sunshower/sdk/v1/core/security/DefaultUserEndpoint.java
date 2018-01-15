package io.sunshower.sdk.v1.core.security;

import io.sunshower.common.Identifier;
import io.sunshower.core.security.UserService;
import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.lang.BooleanElement;
import io.sunshower.sdk.v1.endpoints.core.security.UserEndpoint;
import io.sunshower.sdk.v1.model.core.Users;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultUserEndpoint implements UserEndpoint {

  @Inject private Users users;
  @Inject private UserService userService;
  @PersistenceContext private EntityManager entityManager;

  @Override
  @Transactional
  @PreAuthorize("hasAuthority('admin')")
  public BooleanElement delete(Identifier userid) {
      User user = entityManager.find(User.class, userid);
      if(user == null) {
          return BooleanElement.False;
      } 
      final List<Role> roles = new ArrayList<>(user.getRoles());
      for(Role role : roles) {
          user.removeRole(role);
      }
      entityManager.remove(user);
      return BooleanElement.True;
  }

  @Override
  public List<PrincipalElement> list(boolean active) {
    List<User> results = active ? userService.activeUsers() : userService.inactiveUsers();
    return results.stream().map(users::toElement).collect(Collectors.toList());
  }
}
