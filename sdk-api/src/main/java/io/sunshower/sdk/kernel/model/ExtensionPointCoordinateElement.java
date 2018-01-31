package io.sunshower.sdk.kernel.model;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "coordinate")
public class ExtensionPointCoordinateElement
    extends AbstractElement<ExtensionPointCoordinateElement> {

  {
    setType(ExtensionPointCoordinateElement.class);
  }

  @XmlAttribute private String name;

  @XmlAttribute private String group;

  @XmlAttribute private String version;

  @XmlAttribute private String namespace;
}
