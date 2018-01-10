package io.sunshower.sdk.core.model;

import io.sunshower.model.core.auth.Authentication;
import io.sunshower.sdk.v1.model.core.Roles;
import io.sunshower.sdk.v1.model.core.Users;
import io.sunshower.sdk.v1.model.core.security.AuthenticationElement;
import org.mapstruct.*;

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
}
