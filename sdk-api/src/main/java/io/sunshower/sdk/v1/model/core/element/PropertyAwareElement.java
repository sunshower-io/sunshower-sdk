package io.sunshower.sdk.v1.model.core.element;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyAwareElement<E extends PropertyAwareElement<E>> extends AbstractElement<E> {

  @XmlElement(name = "property")
  @XmlElementWrapper(name = "properties")
  private List<PropertyElement> properties;

  public void addProperty(PropertyElement el) {
    if (properties == null) {
      properties = new ArrayList<>();
    }
    properties.add(el);
  }
}
