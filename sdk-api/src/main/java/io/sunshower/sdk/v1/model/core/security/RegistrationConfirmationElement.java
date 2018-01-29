package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "confirmation")
public class RegistrationConfirmationElement extends AbstractElement<RegistrationConfirmationElement> {

    @XmlAttribute(name = "expires")
    private Date expires;
    
    @XmlAttribute(name = "requested-on")
    private Date requested;
    
   
    @XmlAttribute(name = "registration-id")
    private String registrationId;
   
    @XmlElement
    private PrincipalElement principal;
    
    {
        setType(RegistrationConfirmationElement.class);
    }
    
    
}
