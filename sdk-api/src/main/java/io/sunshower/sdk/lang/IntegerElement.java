package io.sunshower.sdk.lang;

import io.sunshower.sdk.v1.model.core.element.Value;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@XmlRootElement(name = "boolean")
public class IntegerElement extends Value<IntegerElement, Integer> {
    public IntegerElement(Integer value) {
        super(IntegerElement.class, value);
    }
}
