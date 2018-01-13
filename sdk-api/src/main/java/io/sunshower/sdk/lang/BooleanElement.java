package io.sunshower.sdk.lang;

import io.sunshower.sdk.v1.model.core.element.Value;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "boolean")
public class BooleanElement extends Value<BooleanElement, Boolean> {

  @XmlTransient public static final transient BooleanElement True = new BooleanElement(true);

  @XmlTransient public static final transient BooleanElement False = new BooleanElement(false);

  protected BooleanElement() {}

  public BooleanElement(Boolean b) {
    super(BooleanElement.class, b);
  }

  public static BooleanElement valueOf(boolean v) {
    return v ? True : False;
  }
}
