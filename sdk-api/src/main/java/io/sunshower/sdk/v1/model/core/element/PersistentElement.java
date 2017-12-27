package io.sunshower.sdk.v1.model.core.element;

import io.sunshower.sdk.core.Endpoint;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by haswell on 5/11/17.
 */
@XmlTransient
public interface PersistentElement<T extends Serializable, E extends PersistentElement<T, E>> {


    T save(Endpoint<T, E> endpoint);

    E delete(Endpoint<T, E> endpoint);




}
