package io.sunshower.sdk.v1.model.core.faults;

import io.sunshower.sdk.faults.Fault;
import io.sunshower.sdk.v1.model.core.element.ClassElement;
import javax.ws.rs.core.Response;

/** Created by haswell on 3/17/17. */
public class NullElementException extends Fault {

  public NullElementException() {}

  public NullElementException(Class<?> type) {
    super(Response.status(Response.Status.BAD_REQUEST).entity(new ClassElement<>(type)).build());
  }
}
