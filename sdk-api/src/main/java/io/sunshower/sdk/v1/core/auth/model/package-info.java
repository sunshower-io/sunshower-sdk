/** Created by haswell on 3/17/17. */
@XmlAccessorType(XmlAccessType.NONE)
@XmlJavaTypeAdapters({
  @XmlJavaTypeAdapter(type = Identifier.class, value = IdentifierConverter.class),
  @XmlJavaTypeAdapter(type = Class.class, value = ClassConverter.class)
})
package io.sunshower.sdk.v1.core.auth.model;

import io.sunshower.common.Identifier;
import io.sunshower.common.rs.IdentifierConverter;
import io.sunshower.sdk.v1.model.core.converters.ClassConverter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
