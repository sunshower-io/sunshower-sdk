package io.sunshower.sdk.v1.model.core.element;

import javax.xml.bind.annotation.XmlAttribute;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyElement extends AbstractElement<PropertyElement> {

  @XmlAttribute(name = "property-type")
  private PropertyType propertyType;

  @XmlAttribute private String key;

  @XmlAttribute private String name;

  @XmlAttribute private String value;

  {
    setType(PropertyElement.class);
  }
}
