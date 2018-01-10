package io.sunshower.sdk.v1.model.core;

import io.sunshower.model.core.auth.Role;
import io.sunshower.sdk.v1.model.core.security.RoleElement;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Roles {

  @Mappings({
    @Mapping(source = "id", target = "id"),
    @Mapping(source = "authority", target = "authority")
  })
  RoleElement toElement(Role role);

  @InheritInverseConfiguration
  Role toModel(RoleElement element);

  List<RoleElement> toElement(Set<Role> roles);

  List<RoleElement> toElement(List<Role> roles);

  List<Role> toModel(List<RoleElement> roles);
}
