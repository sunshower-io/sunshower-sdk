package io.sunshower.sdk.v1.model.core.element;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "value")
public abstract class Value<T extends Value<T, V>, V> extends Element<T> {
    
    @XmlAnyElement(lax = true)
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
