package io.sunshower.sdk.core.model;

import io.sunshower.model.core.auth.Activation;
import io.sunshower.sdk.v1.model.core.Users;
import org.mapstruct.*;

@Mapper(
  uses = Users.class,
  componentModel = "jsr330",
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface Activations {

  @Mappings({
    @Mapping(source = "id", target = "id"),
    @Mapping(target = "application", ignore = true),
    @Mapping(target = "activator", source = "activator"),
    @Mapping(source = "activator.id", target = "activator.id"),
    @Mapping(target = "activator.password", ignore = true),
    @Mapping(source = "activationDate", target = "activationDate")
  })
  ActivationElement toElement(Activation activation);

  @InheritInverseConfiguration
  Activation toModel(ActivationElement element);
}
