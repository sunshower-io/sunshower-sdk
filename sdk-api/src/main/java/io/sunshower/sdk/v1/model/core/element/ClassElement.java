package io.sunshower.sdk.v1.model.core.element;

import io.sunshower.sdk.v1.model.core.converters.ClassConverter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by haswell on 3/17/17.
 */
@XmlRootElement(name = "type")
public class ClassElement<T> {

    @XmlElement
    @XmlJavaTypeAdapter(ClassConverter.class)
    private Class<T> type;

    public ClassElement() {

    }

    public ClassElement(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }
}
