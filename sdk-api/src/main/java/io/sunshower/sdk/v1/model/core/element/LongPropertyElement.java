package io.sunshower.sdk.v1.model.core.element;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@XmlRootElement(name = "property")
public class LongPropertyElement extends PropertyElement<Long, LongPropertyElement>{
    
    {
        setType(LongPropertyElement.class);
    }

    public LongPropertyElement(String key, String name, Long value) {
        super(key, name, value);
    }
}
