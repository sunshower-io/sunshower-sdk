package io.sunshower.sdk.v1.model.core;

import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.model.core.security.RegistrationConfirmationElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import io.sunshower.service.signup.RegistrationRequest;
import org.mapstruct.*;

@Mapper(
  componentModel = "jsr330",
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  uses = {Roles.class, Users.class}
)
public interface Registrations {
  @Mappings({
    @Mapping(source = "username", target = "username"),
    @Mapping(source = "emailAddress", target = "details.emailAddress")
  })
  User toUser(RegistrationRequestElement request);

  @AfterMapping
  default void setDetailsUser(@MappingTarget User user) {
    user.getDetails().setUser(user);
  }

  @Mappings({
    @Mapping(source = "user.username", target = "username"),
    @Mapping(source = "user.details.emailAddress", target = "emailAddress"),
    @Mapping(source = "requestId", target = "registrationId"),
  })
  RegistrationRequestElement toElement(RegistrationRequest registrationRequest);

  @InheritInverseConfiguration
  RegistrationRequest toModel(RegistrationRequestElement registrationRequestElement);

  @Mappings({
    @Mapping(source = "requestId", target = "registrationId"),
    @Mapping(source = "user.username", target = "principal.username"),
    @Mapping(source = "user.details.firstname", target = "principal.firstName"),
    @Mapping(source = "user.details.lastname", target = "principal.lastName"),
  })
  RegistrationConfirmationElement toConfirmation(RegistrationRequest signup);
}
