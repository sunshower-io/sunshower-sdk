package io.sunshower.sdk.v1.model.core;

import io.sunshower.model.core.Image;
import io.sunshower.model.core.auth.ImageType;
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
  Base64.Decoder decoder = Base64.getDecoder();

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
    @Mapping(source = "firstName", target = "details.firstName"),
    @Mapping(source = "locale", target = "details.locale"),
    @Mapping(source = "lastName", target = "details.lastName"),
    @Mapping(source = "emailAddress", target = "details.emailAddress"),
    @Mapping(source = "phoneNumber", target = "details.phoneNumber")
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
    @Mapping(source = "user.details.firstName", target = "principal.firstName"),
    @Mapping(source = "user.details.lastName", target = "principal.lastName"),
  })
  RegistrationConfirmationElement toConfirmation(RegistrationRequest signup);

  default ImageElement imageElement(Image image) {
    if (image == null) {
      return null;
    }

    val result = new ImageElement();
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

  default Image toImage(ImageElement el) {
    if (el == null) {
      return null;
    }
    val data = el.getData();
    val result = new Image();
    if (data != null) {
      result.setData(decoder.decode(data));
    }
    val type = el.getFormat();
    if (type != null) {
      val normalized = type.toLowerCase().trim();
      switch (normalized) {
        case "svg":
          {
            result.setType(ImageType.SVG);
            break;
          }
        case "gif":
          {
            result.setType(ImageType.GIF);
            break;
          }

        case "png":
          {
            result.setType(ImageType.PNG);
            break;
          }

        case "jpg":
          {
            result.setType(ImageType.JPG);
            break;
          }
      }
    }
    return result;
  }
}
