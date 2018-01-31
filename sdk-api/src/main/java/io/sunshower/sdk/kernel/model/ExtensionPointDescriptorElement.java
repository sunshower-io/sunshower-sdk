package io.sunshower.sdk.kernel.model;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "extension-point")
public class ExtensionPointDescriptorElement
    extends AbstractElement<ExtensionPointDescriptorElement> {

  {
    setType(ExtensionPointDescriptorElement.class);
  }

  @XmlAttribute(name = "location")
  private String location;

  @XmlAttribute(name = "extension-type")
  private Class<?> extensionType;

  @XmlElement(name = "coordinates")
  private ExtensionPointCoordinateElement coordinates;
}
