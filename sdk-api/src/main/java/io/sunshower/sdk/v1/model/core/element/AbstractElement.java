package io.sunshower.sdk.v1.model.core.element;

import io.sunshower.common.Identifier;
import io.sunshower.common.rs.LaxIdentifierConverter;
import io.sunshower.common.rs.TypeAttributeClassExtractor;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.oxm.annotations.XmlClassExtractor;
import org.eclipse.persistence.oxm.annotations.XmlIDExtension;

@XmlRootElement(name = "element")
@XmlClassExtractor(TypeAttributeClassExtractor.class)
public class AbstractElement<E extends PersistentElement<Identifier, E>>
    extends PersistentElement<Identifier, E> {

  @XmlID
  @Getter
  @Setter
  @XmlIDExtension
  @XmlAttribute(name = "id")
  @XmlJavaTypeAdapter(LaxIdentifierConverter.class)
  private Identifier id;

  public AbstractElement() {}

  public AbstractElement(Class<E> type) {
    this(type, Identifier.random());
  }

  public AbstractElement(Class<E> type, Identifier id) {
    super(type, id);
  }
}
