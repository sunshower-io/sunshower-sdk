package io.sunshower.sdk.v1.model.core.element;

import lombok.*;
import org.eclipse.persistence.oxm.XMLRoot;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyElement extends AbstractElement<PropertyElement> {

  @XmlAttribute(name = "property-type")
  private PropertyType propertyType;

  @XmlAttribute private String value;

  @XmlAttribute private String key;

  @XmlAttribute private String name;

  {
    setType(PropertyElement.class);
  }

}
