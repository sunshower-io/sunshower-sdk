package io.sunshower.sdk.v1.model.core.element;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PropertyAwareElement<E extends PropertyAwareElement<E>> extends AbstractElement<E> {

  @XmlElement
  @XmlElementWrapper(name = "property")
  private List<PropertyElement<?, ?>> properties;

  public void addProperty(PropertyElement<?, ?> el) {
    if (properties == null) {
      properties = new ArrayList<>();
    }
    properties.add(el);
  }
  
  
}
