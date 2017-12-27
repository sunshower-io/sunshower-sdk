package io.sunshower.sdk.v1.model.core.element;

import io.sunshower.common.Identifier;
import io.sunshower.common.rs.IdentifierConverter;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "link")
public class LinkElement extends AbstractElement<LinkElement> {

    
    @XmlAttribute
    @XmlJavaTypeAdapter(IdentifierConverter.class)
    private Identifier source;


    @XmlAttribute
    @XmlJavaTypeAdapter(IdentifierConverter.class)
    private Identifier target;

    public LinkElement() {
        super(LinkElement.class);
    }

    public Identifier getSource() {
        return source;
    }

    public void setSource(Identifier source) {
        this.source = source;
    }

    public Identifier getTarget() {
        return target;
    }

    public void setTarget(Identifier target) {
        this.target = target;
    }
}
