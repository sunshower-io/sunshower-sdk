package io.sunshower.sdk.v1.model.core.element;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.oxm.XMLRoot;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.stream.Stream;

@Getter
@Setter
public class PropertyElement<U, T extends PropertyElement<U, T>> extends AbstractElement<T> {

  @XmlAttribute String key;

  @XmlAttribute String name;

  @XmlAnyElement(lax = true)
  private U value;

  private final transient Method valueOf;

  public PropertyElement() {
    this.valueOf = configure();
  }

  public PropertyElement(String key, String name, U value) {
    this();
    this.key = key;
    this.name = name;
    this.value = value;
  }
  
  

  @SuppressWarnings("unchecked")
  protected void doUnmarshal() {
    try {
      this.value = (U) valueOf.invoke(this, ((XMLRoot) value).getObject());
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @SuppressWarnings("unchecked")
  public void afterUnmarshal(Unmarshaller u, Object parent) {
    doUnmarshal();
  }

  protected Method configure() {
    final ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
    final Class<?> valueType = (Class<?>) superclass.getActualTypeArguments()[0];
    return Stream.of(valueType.getDeclaredMethods())
        .filter(t -> t.getName().equals("valueOf") && t.getParameterCount() == 1)
        .filter(
            t -> {
              final Class<?>[] ptypes = t.getParameterTypes();
              final Class<?> ptype = ptypes[0];
              return String.class.equals(ptype) || Object.class.equals(ptype);
            })
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Type does not supply a public, static method valueOf() accepting a string"));
  }
}
