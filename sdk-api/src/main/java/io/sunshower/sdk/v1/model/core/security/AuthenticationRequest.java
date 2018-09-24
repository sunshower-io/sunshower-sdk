package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "authentication")
public class AuthenticationRequest {

  @XmlElement private String username;

  @XmlElement private String password;

  public AuthenticationElement at(SecurityEndpoint securityEndpoint) {
    return securityEndpoint.authenticate(this);
  }
}
