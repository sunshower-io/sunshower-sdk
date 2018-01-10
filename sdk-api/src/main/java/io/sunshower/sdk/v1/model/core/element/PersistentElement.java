package io.sunshower.sdk.v1.model.core.element;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

import io.sunshower.common.Identifier;
import io.sunshower.persist.Identifiers;
import io.sunshower.persist.Sequence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PersistentElement<T extends Serializable, E extends PersistentElement<T, E>>
    extends Element<E> {

  @XmlTransient public static final Sequence<Identifier> sequence = Identifiers.newSequence();

  protected PersistentElement() {}

  protected PersistentElement(Class<E> type, T id) {
    setId(id);
    if (type != null) {
      this.type = type;
    }
  }

  public abstract T getId();

  public abstract void setId(T id);

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PersistentElement<?, ?> element = (PersistentElement<?, ?>) o;
    final Object thisId = getId();
    final Object thatId = element.getId();

    return thisId != null ? thisId.equals(thatId) : thatId == null;
  }

  @Override
  public int hashCode() {
    final Object id = getId();
    return id != null ? id.hashCode() : 0;
  }
}
