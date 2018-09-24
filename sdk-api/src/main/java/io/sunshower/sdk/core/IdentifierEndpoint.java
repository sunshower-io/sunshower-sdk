package io.sunshower.sdk.core;

import io.sunshower.common.Identifier;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("identifiers")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface IdentifierEndpoint {

  @GET
  @Path("/")
  Identifier create();
}
