package io.sunshower.sdk.v1.model.core.element;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum PropertyType {
  @XmlEnumValue("integer")
  Integer,
  @XmlEnumValue("string")
  String,
  @XmlEnumValue("boolean")
  Boolean,
  @XmlEnumValue("secret")
  Secret,
  @XmlEnumValue("class")
  Class
}
