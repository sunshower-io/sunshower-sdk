package io.sunshower.sdk.v1.model.core;

import io.sunshower.model.core.Image;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.model.core.security.RegistrationConfirmationElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;
import io.sunshower.sdk.v1.model.ext.ImageElement;
import io.sunshower.service.signup.Product;
import io.sunshower.service.signup.RegistrationRequest;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.val;
import org.mapstruct.*;

@Mapper(
  componentModel = "jsr330",
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  uses = {Roles.class, Users.class}
)
public interface Registrations {
  Base64.Encoder encoder = Base64.getEncoder();

  @Mappings({})
  default List<String> productToNames(Set<Product> products) {
    if (products == null) {
      return Collections.emptyList();
    }
    return products.stream().map(Product::getName).collect(Collectors.toList());
  }

  @Mappings({
    @Mapping(target = "id", ignore = true),
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
    @Mapping(target = "type", ignore = true),
    @Mapping(target = "image", expression = "java(imageElement(registrationRequest.getUser()))")
  })
  RegistrationRequestElement toElement(RegistrationRequest registrationRequest);

  default ImageElement imageElement(User user) {
    if (user == null) {
      return null;
    }
    val details = user.getDetails();
    if (details == null) {
      return null;
    }
    val image = details.getImage();
    if (image == null) {
      return null;
    }
    return imageElement(image);
  }

  @InheritInverseConfiguration
  @Mappings({@Mapping(target = "products", ignore = true)})
  RegistrationRequest toModel(RegistrationRequestElement registrationRequestElement);

  @Mappings({
    @Mapping(source = "requestId", target = "registrationId"),
    @Mapping(source = "user.username", target = "principal.username"),
    @Mapping(source = "user.details.firstname", target = "principal.firstName"),
    @Mapping(source = "user.details.lastname", target = "principal.lastName"),
  })
  RegistrationConfirmationElement toConfirmation(RegistrationRequest signup);

  default ImageElement imageElement(Image image) {
    if (image == null) {
      return null;
    }

    final ImageElement result = new ImageElement();
    if (image.getType() != null) {
      result.setFormat(image.getType().name());
    }

    if (image.getData() != null) {
      result.setData(encoder.encodeToString(image.getData()));
    }

    if (image.getData() != null) {
      result.setData(encoder.encodeToString(image.getData()));
    }
    return result;
  }
}
