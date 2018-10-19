package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import io.sunshower.sdk.v1.model.ext.ImageElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(builderMethodName = "create", buildMethodName = "newPrincipal")
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "principal")
@EqualsAndHashCode(callSuper = false)
public class PrincipalElement extends AbstractElement<PrincipalElement> {

  @XmlAttribute(name = "username")
  private String username;

  @XmlAttribute(name = "first-name")
  private String firstName;

  @XmlElement(name = "last-name")
  private String lastName;

  @XmlAttribute(name = "email-address")
  private String emailAddress;

  @XmlAttribute(name = "phone-number")
  private String phoneNumber;

  @XmlAttribute(name = "password")
  private String password;

  @XmlElement(name = "role")
  @XmlElementWrapper(name = "roles")
  private List<RoleElement> roles;

  @XmlAttribute private Locale locale;

  @XmlAttribute private boolean active;

  @XmlAttribute private Date registered;

  @XmlAttribute(name = "last-active")
  private Date lastActive;

  @XmlAttribute(name = "active-until")
  private Date activeUntil;

  @XmlElement(name = "image")
  private ImageElement image;

  @XmlAttribute(name = "details-id")
  private Identifier detailsId;

  {
    setType(PrincipalElement.class);
  }

  public void addRole(RoleElement role) {
    if (roles == null) {
      roles = new ArrayList<>();
    }
    roles.add(role);
  }
}
