package io.sunshower.sdk.v1.model.core.element;

import io.sunshower.sdk.v1.model.core.converters.ValueAdapter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@ToString
@XmlRootElement(name = "value")
public abstract class Value<T extends Value<T, V>, V> extends Element<T> {

    @XmlAttribute
    @XmlJavaTypeAdapter(ValueAdapter.class)
    private V value;
    
    protected Value() {
        
    }
    
    
    protected Value(Class<T> type, V value) {
        super(type);
        this.value = value;
    }
   
    public V getValue() {
        return value;
    }
}
