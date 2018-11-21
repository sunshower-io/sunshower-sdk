package io.sunshower.sdk.v1.model.ext;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "image")
public class ImageElement extends AbstractElement<ImageElement> {

  {
    setType(ImageElement.class);
  }

  @XmlAttribute(name = "encoding")
  private String encoding;

  @XmlAttribute(name = "format")
  private String format;

  @XmlElement private String data;

  @XmlAttribute private String name;
}
