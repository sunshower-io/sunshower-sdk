package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by haswell on 5/5/17.
 */
@XmlRootElement(name = "authentication")
public class AuthenticationRequest {

    @XmlElement
    private String username;


    @XmlElement
    private String password;

    public AuthenticationRequest() {

    }


    public AuthenticationRequest(String username, String password) {
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

    public AuthenticationElement at(SecurityEndpoint securityEndpoint) {
        return securityEndpoint.authenticate(this);
    }
}
