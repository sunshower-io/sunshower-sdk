package io.sunshower.sdk.v1.model.core.element;

import io.sunshower.common.rs.ClassAdapter;
import io.sunshower.common.rs.TypeAttributeClassExtractor;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.oxm.annotations.XmlClassExtractor;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

@Getter
@Setter
@XmlRootElement(name = "element")
@XmlDiscriminatorNode("@type")
@XmlClassExtractor(TypeAttributeClassExtractor.class)
public class Element<T> {

  @XmlAttribute(name = "type")
  @XmlJavaTypeAdapter(ClassAdapter.class)
  protected Class<T> type;

  protected Element() {}

  protected Element(Class<T> type) {
    setType(type);
  }
}
