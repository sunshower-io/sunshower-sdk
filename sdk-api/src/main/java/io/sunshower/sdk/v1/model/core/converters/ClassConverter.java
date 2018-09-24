package io.sunshower.sdk.v1.model.core.converters;

import io.sunshower.sdk.v1.model.core.faults.InvalidElementException;
import io.sunshower.sdk.v1.model.core.faults.NullElementException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/** Created by haswell on 3/17/17. */
public class ClassConverter extends XmlAdapter<String, Class<?>> {
  @Override
  public String marshal(Class<?> v) throws Exception {
    return v == null ? void.class.getName() : v.getName();
  }

  @Override
  public Class<?> unmarshal(String v) throws Exception {
    if (v == null) {
      throw new NullElementException(void.class);
    }
    try {
      return Class.forName(v);
    } catch (Exception ex) {
      throw new InvalidElementException("%s is not a valid type", void.class, v);
    }
  }
}
