package io.sunshower.sdk.test;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement
public class Adap extends AbstractElement<Adap> {

  {
    setType(Adap.class);
  }

  @XmlAttribute private String name = UUID.randomUUID().toString();
}
