package io.sunshower.sdk.v1.model.core.security;


import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "token")
public class AuthenticationTokenElement extends AbstractElement<AuthenticationTokenElement> {

    @XmlAttribute
    private String value;
    
    {
        setType(AuthenticationTokenElement.class);
    }

}
