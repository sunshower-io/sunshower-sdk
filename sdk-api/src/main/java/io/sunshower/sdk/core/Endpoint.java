package io.sunshower.sdk.core;

import io.sunshower.sdk.v1.model.core.element.PersistentElement;
import java.io.Serializable;

/** Created by haswell on 5/9/17. */
public interface Endpoint<T extends Serializable, E extends PersistentElement<T, E>> {

  T save(E e);

  E delete(T id);
}
