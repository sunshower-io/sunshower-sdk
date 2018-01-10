package io.sunshower.sdk.core.model;

public interface Transformer<M, E> {

  M toModel(E v);

  E toElement(M t);
}
