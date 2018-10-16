package io.sunshower.sdk.v1.endpoints.ext;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.v1.model.ext.ChangeIconRequest;
import io.sunshower.sdk.v1.model.ext.ImageElement;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("icon")
public interface IconEndpoint {

  @PUT
  @Path("{id}")
  void setIcon(@PathParam("id") Identifier id, ChangeIconRequest request);

  @GET
  @Path("{type}/{id}")
  ImageElement getIcon(@PathParam("type") Class<?> type, @PathParam("id") Identifier id);
}
