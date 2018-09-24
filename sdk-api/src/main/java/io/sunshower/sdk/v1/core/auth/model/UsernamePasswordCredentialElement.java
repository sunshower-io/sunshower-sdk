package io.sunshower.sdk.v1.core.auth.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.ToString;

@ToString
@XmlRootElement(name = "username-password")
public class UsernamePasswordCredentialElement extends CredentialElement {

  @XmlAttribute private String username;

  @XmlElement private String password;

  public UsernamePasswordCredentialElement() {
    super();
  }

  public UsernamePasswordCredentialElement(String username, String password) {
    this();
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
