package io.sunshower.sdk.v1.model.core.faults.authorization;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/** Created by haswell on 5/5/17. */
public class AuthenticationFailedException extends WebApplicationException {

  public AuthenticationFailedException() {
    super(Response.Status.UNAUTHORIZED);
  }
}
