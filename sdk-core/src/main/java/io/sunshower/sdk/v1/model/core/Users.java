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
    @Mapping(target = "password", ignore = true),
    @Mapping(source = "user.details.emailAddress", target = "emailAddress")
  })
  PrincipalElement toElement(User user);

  @InheritInverseConfiguration
  @Mappings({
    @Mapping(target = "id", ignore = true),
    @Mapping(target = "roles", ignore = true),
    @Mapping(target = "password", source = "password"),
  })
  User toModel(PrincipalElement principalElement);
}
