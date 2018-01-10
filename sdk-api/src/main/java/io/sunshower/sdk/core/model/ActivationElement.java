package io.sunshower.sdk.core.model;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@ToString
@XmlRootElement
public class ActivationElement extends AbstractElement<ActivationElement> {
  @XmlAttribute private String name;
  @XmlAttribute private Boolean active;

  @XmlElement private PrincipalElement activator;

  @XmlAttribute(name = "activation-date")
  private Date activationDate;

  @XmlElement private ApplicationElement application;

}
