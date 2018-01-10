package io.sunshower.sdk.v1.model.core.security;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "token")
public class AuthenticationTokenElement {

    @XmlAttribute
    private String value;

    public AuthenticationTokenElement() {

    }


    public AuthenticationTokenElement(String token) {
        this.value = token;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
