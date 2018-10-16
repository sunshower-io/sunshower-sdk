package io.sunshower.sdk.v1.model.ext;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "icon")
public class ChangeIconRequest extends AbstractElement<ChangeIconRequest> {

  {
    setType(ChangeIconRequest.class);
  }

  @XmlAttribute(name = "target-type")
  private Class<?> targetType;

  @XmlAttribute(name = "target-id")
  private Identifier targetId;

  @XmlElement(name = "contents")
  private ImageElement imageElement;
}
