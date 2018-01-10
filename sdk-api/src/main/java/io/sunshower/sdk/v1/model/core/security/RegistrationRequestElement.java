package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
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

  public RegistrationRequestElement() {
    super(RegistrationRequestElement.class);
  }
}
