package io.sunshower.sdk.v1.model.core.element;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "property")
@NoArgsConstructor
public class StringPropertyElement extends PropertyElement<String, StringPropertyElement> {
    {
        setType(StringPropertyElement.class);
    }


    public StringPropertyElement(String key, String name, String value) {
        super(key, name, value);
    }
}
