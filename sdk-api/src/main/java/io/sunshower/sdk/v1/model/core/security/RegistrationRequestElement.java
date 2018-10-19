package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.sdk.core.jaxrs.LocaleAdapter;
import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import io.sunshower.sdk.v1.model.ext.ImageElement;
import java.util.List;
import java.util.Locale;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "signup")
@Builder(buildMethodName = "create", builderMethodName = "newRegistration")
public class RegistrationRequestElement extends AbstractElement<RegistrationRequestElement> {

  @XmlAttribute(name = "registration-id")
  private String registrationId;

  @XmlElement(name = "username")
  private String username;

  @XmlElement(name = "password")
  private String password;

  @XmlElement(name = "phone-number")
  private String phoneNumber;

  @XmlElement(name = "email-address")
  private String emailAddress;

  @XmlElement(name = "first-name")
  private String firstName;

  @XmlElement(name = "last-name")
  private String lastName;

  @XmlAttribute(name = "locale")
  @XmlJavaTypeAdapter(LocaleAdapter.class)
  private Locale locale;

  @XmlElement private ImageElement image;

  @XmlElement private List<String> products;

  {
    setType(RegistrationRequestElement.class);
  }
}
