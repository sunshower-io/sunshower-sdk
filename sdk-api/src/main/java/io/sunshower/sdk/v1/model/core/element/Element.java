package io.sunshower.sdk.v1.model.core.element;



import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

import io.sunshower.common.Identifier;
import io.sunshower.common.rs.ClassAdapter;
import io.sunshower.common.rs.TypeAttributeClassExtractor;
import io.sunshower.persist.Identifiers;
import io.sunshower.persist.Sequence;
import io.sunshower.sdk.core.Endpoint;
import io.sunshower.sdk.v1.model.core.security.RoleElement;
import org.eclipse.persistence.oxm.annotations.XmlClassExtractor;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

/**
 * Created by haswell on 3/15/17.
 */
@XmlRootElement(name = "element")
@XmlDiscriminatorNode("@type")
@XmlClassExtractor(TypeAttributeClassExtractor.class)
public abstract class Element<
        T extends Serializable,
        E extends Element<T, E>
        > implements PersistentElement<T, E> {
    
    @XmlTransient
    public static final Sequence<Identifier> sequence = Identifiers.newSequence();

    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(ClassAdapter.class)
    private Class<?> type;
    
    protected Element() {
        
    }

    protected Element(Class<E> type, T id) {
        setId(id);
        if(type != null) {
            this.type = type;
        }
    }

    public abstract T getId();

    public abstract void setId(T id);

    @Override
    public T save(Endpoint<T, E> endpoint) {
//        return endpoint.save(type.cast(this));
        return (T) this;
    }

    @Override
    public E delete(Endpoint<T, E> endpoint) {
        return endpoint.delete(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element<?, ?> element = (Element<?, ?>) o;
        final Object thisId = getId();
        final Object thatId = element.getId();

        return thisId != null ? thisId.equals(thatId) : thatId == null;
    }

    @Override
    public int hashCode() {
        final Object id = getId();
        return id != null ? id.hashCode() : 0;
    }


}
