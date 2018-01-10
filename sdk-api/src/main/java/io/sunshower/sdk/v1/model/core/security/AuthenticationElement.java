package io.sunshower.sdk.v1.model.core.security;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "authentication")
public class AuthenticationElement {

    @XmlElement
    private PrincipalElement principal;

    @XmlElement
    private AuthenticationTokenElement token;

    public PrincipalElement getPrincipal() {
        return principal;
    }

    public void setPrincipal(PrincipalElement principal) {
        this.principal = principal;
    }

    public AuthenticationTokenElement getToken() {
        return token;
    }

    public void setToken(AuthenticationTokenElement token) {
        this.token = token;
    }
}
