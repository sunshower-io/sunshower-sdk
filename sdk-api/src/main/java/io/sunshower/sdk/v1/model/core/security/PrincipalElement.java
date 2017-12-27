package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by haswell on 5/5/17.
 */

@XmlRootElement(name = "principal")
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


    @XmlElement
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<RoleElement> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleElement> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "PrincipalElement{" +
                "active=" + active +
                ", registered=" + registered +
                ", lastActive=" + lastActive +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roles=" + roles +
                '}';
    }
}
