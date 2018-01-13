package io.sunshower.sdk.core;

import io.sunshower.sdk.core.model.ActivationElement;
import io.sunshower.sdk.lang.BooleanElement;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("activate")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface ActivationEndpoint {

  @GET
  BooleanElement isActive();

  @POST
  @Path("/")
  ActivationElement activate(PrincipalElement element);
}
