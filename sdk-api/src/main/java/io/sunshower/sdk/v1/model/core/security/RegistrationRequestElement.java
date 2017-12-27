package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by haswell on 5/5/17.
 */
@XmlRootElement(name = "signup")
public class RegistrationRequestElement extends AbstractElement<RegistrationRequestElement> {

    @XmlAttribute(name = "registration-id")
    private String registrationId;

    @XmlElement(name = "username")
    private String username;

    @XmlElement(name = "password")
    private String password;


    @XmlElement(name = "phone-number")
    private String phoneNumber;

    @XmlElement(name = "email-address")
    private String emailAddress;


    @XmlElement(name = "first-name")
    private String firstName;

    @XmlElement(name  = "last-name")
    private String lastName;

    public RegistrationRequestElement() {
        super(RegistrationRequestElement.class);
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void submit(SignupEndpoint signupEndpoint) {
        signupEndpoint.signup(this);
    }
}
