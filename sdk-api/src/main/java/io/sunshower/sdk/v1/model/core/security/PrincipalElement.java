package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@XmlRootElement(name = "principal")
@EqualsAndHashCode(callSuper = false)
public class PrincipalElement extends AbstractElement<PrincipalElement> {

    @XmlAttribute
    private boolean active;


    @XmlAttribute
    private Date registered;

    @XmlAttribute
    private Date lastActive;

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


    @XmlElement(name = "role")
    @XmlElementWrapper(name = "roles")
    private List<RoleElement> roles;

    public PrincipalElement() {
        super(PrincipalElement.class);
    }
    
    public void addRole(RoleElement role) {
        if(roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }

}
