package io.sunshower.sdk.v1.endpoints.core.security;

import io.sunshower.sdk.v1.model.core.security.AuthenticationElement;
import io.sunshower.sdk.v1.model.core.security.AuthenticationRequest;
import io.sunshower.sdk.v1.model.core.security.AuthenticationTokenElement;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Produces({
  MediaType.APPLICATION_JSON,
  MediaType.APPLICATION_XML,
})
@Consumes({
  MediaType.APPLICATION_JSON,
  MediaType.APPLICATION_XML,
})
@Path("security")
public interface SecurityEndpoint {

  @PUT
  @Path("validate")
  void validate(AuthenticationTokenElement element);

  @POST
  @Path("authenticate")
  AuthenticationElement authenticate(AuthenticationRequest request);

  @PUT
  @Path("token/authenticate")
  AuthenticationElement authenticate(AuthenticationTokenElement token);
}
