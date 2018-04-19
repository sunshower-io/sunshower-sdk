package io.sunshower.sdk.core.model;

import io.sunshower.model.core.auth.Authentication;
import io.sunshower.model.core.auth.Role;
import io.sunshower.sdk.v1.model.core.Roles;
import io.sunshower.sdk.v1.model.core.Users;
import io.sunshower.sdk.v1.model.core.security.AuthenticationElement;
import io.sunshower.sdk.v1.model.core.security.RoleElement;
import org.mapstruct.*;

import java.util.*;

@Mapper(
  componentModel = "jsr330",
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  uses = {Roles.class, Users.class, Tokens.class}
)
public interface Authentications {

  @Mappings(@Mapping(source = "principal", target = "user"))
  Authentication toModel(AuthenticationElement element);

  @InheritInverseConfiguration
  AuthenticationElement toElement(Authentication authentication);

  static List<RoleElement> mapRoles(Role root) {
    if (root == null) {
      return null;
    }
    Queue<Role> set = new LinkedList<>();
    List<RoleElement> roleElements = new ArrayList<>();
    set.add(root);
    while (!set.isEmpty()) {
      Role poll = set.poll();
      roleElements.add(mapRole(poll));
      Set<Role> children = poll.getChildren();
      if (children != null) {
        set.addAll(children);
      }
    }
    return roleElements;
  }

  static RoleElement mapRole(Role poll) {
    RoleElement e = new RoleElement();
    e.setAuthority(poll.getAuthority());
    return e;
  }
}
