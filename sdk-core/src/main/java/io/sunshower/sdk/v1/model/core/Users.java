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
  @Named("toElement")
  PrincipalElement toElement(User user);

  @Mappings({
    @Mapping(source = "id", target = "id"),
    @Mapping(source = "username", target = "username"),
    @Mapping(source = "active", target = "active"),
    @Mapping(source = "roles", target = "roles"),
    @Mapping(target = "password", ignore = true),
    @Mapping(source = "user.details.emailAddress", target = "emailAddress"),
    @Mapping(source = "user.details.phoneNumber", target = "phoneNumber"),
    @Mapping(source = "user.details.lastName", target = "lastName"),
    @Mapping(source = "user.details.name", target = "firstName"),
    @Mapping(source = "user.details.activeUntil", target = "activeUntil"),
    @Mapping(source = "user.details.registered", target = "registered"),
    @Mapping(source = "user.details.lastActive", target = "lastActive"),
    @Mapping(source = "user.details.id", target = "detailsId"),
    @Mapping(source = "user.details.locale", target = "locale")
  })
  PrincipalElement completeElement(User user);

  @InheritInverseConfiguration(name = "toElement")
  @Mappings({
    @Mapping(target = "id", ignore = true),
    @Mapping(target = "roles", ignore = true),
    @Mapping(target = "password", source = "password"),
  })
  User toModel(PrincipalElement principalElement);
}
