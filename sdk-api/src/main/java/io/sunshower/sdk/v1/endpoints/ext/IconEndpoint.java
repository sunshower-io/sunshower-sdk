package io.sunshower.sdk.v1.endpoints.ext;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.v1.model.ext.ChangeIconRequest;
import io.sunshower.sdk.v1.model.ext.ImageElement;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("icon")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface IconEndpoint {

  @PUT
  @Path("reset")
  void reset();

  @PUT
  @Path("{id}")
  void setIcon(@PathParam("id") Identifier id, ChangeIconRequest request);

  @GET
  @Path("{type}/{id}")
  ImageElement getIcon(@PathParam("type") Class<?> type, @PathParam("id") Identifier id);
}
