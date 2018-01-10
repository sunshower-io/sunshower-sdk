package io.sunshower.sdk.v1.model.core;

import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import org.mapstruct.*;

@Mapper(
  componentModel = "jsr330",
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  uses = Roles.class
)
public interface Users {

  @Mappings({
    @Mapping(source = "id", target = "id"),
    @Mapping(source = "username", target = "username"),
    @Mapping(source = "active", target = "active"),
    @Mapping(source = "roles", target = "roles"),
    @Mapping(source = "user.details.emailAddress", target = "emailAddress")
  })
  PrincipalElement toElement(User user);

  @InheritInverseConfiguration
  User toModel(PrincipalElement principalElement);
}
