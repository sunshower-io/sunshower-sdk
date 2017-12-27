package io.sunshower.sdk.v1.model.core.security;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by haswell on 5/10/17.
 */
@XmlRootElement(name = "role")
public class RoleElement {
    
    @XmlAttribute
    private String authority;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
