package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@XmlRootElement(name = "role")
public class RoleElement extends AbstractElement<RoleElement> {

  @XmlAttribute private String authority;
  
  public RoleElement() {
      super(RoleElement.class);
  }
}
