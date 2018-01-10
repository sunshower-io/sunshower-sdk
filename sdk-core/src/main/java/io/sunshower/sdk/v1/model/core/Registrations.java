package io.sunshower.sdk.v1.model.core;

import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import io.sunshower.service.signup.RegistrationRequest;
import org.mapstruct.*;

@Mapper(
  componentModel = "jsr330",
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  uses = Roles.class
)
public interface Registrations {
  @Mappings({@Mapping(source = "username", target = "username")})
  User toUser(RegistrationRequestElement request);

  RegistrationRequestElement toElement(RegistrationRequest registrationRequest);

  //    @Mappings({
  //            @Mapping(source = "id", target = "id"),
  //            @Mapping(source = "authority", target = "authority")
  //    })
  //    RegistrationRequestElement toElement(RegistrationRequest request);
  //
  //    @InheritInverseConfiguration
  //    RegistrationRequest toModel(RegistrationRequest element);
  //
  //
  //    User toUser(RegistrationRequestElement request);

}
