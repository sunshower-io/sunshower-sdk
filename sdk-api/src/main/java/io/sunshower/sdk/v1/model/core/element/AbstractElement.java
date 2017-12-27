package io.sunshower.sdk.v1.model.core.element;

import io.sunshower.common.Identifier;
import io.sunshower.common.rs.IdentifierConverter;
import io.sunshower.common.rs.TypeAttributeClassExtractor;
import org.eclipse.persistence.oxm.annotations.XmlClassExtractor;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;
import org.eclipse.persistence.oxm.annotations.XmlIDExtension;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by haswell on 5/11/17.
 */
@XmlRootElement(name = "element")
@XmlClassExtractor(TypeAttributeClassExtractor.class)
public class AbstractElement<E extends Element<Identifier, E>> extends Element<Identifier, E> {

    @XmlID
    @XmlIDExtension
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(IdentifierConverter.class)
    private Identifier id;
    
    public AbstractElement() {
    }

    public AbstractElement(Class<E> type) {
        this(type, Identifier.random());
    }

    public AbstractElement(Class<E> type, Identifier id) {
        super(type, id);
    }



    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public void setId(Identifier id) {
        this.id = id;
    }



}
