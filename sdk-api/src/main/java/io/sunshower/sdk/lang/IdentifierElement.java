package io.sunshower.sdk.lang;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.v1.model.core.element.Value;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "id")
public class IdentifierElement extends Value<IdentifierElement, Identifier> {

    public IdentifierElement() {
        
    }
    
    public IdentifierElement(Identifier value) {
        super(IdentifierElement.class, value);
    }
    
    public static IdentifierElement random() {
        return new IdentifierElement(Identifier.random());
    }
}
