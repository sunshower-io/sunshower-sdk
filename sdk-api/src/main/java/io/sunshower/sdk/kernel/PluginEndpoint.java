package io.sunshower.sdk.kernel;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.kernel.model.PluginDescriptorElement;
import io.sunshower.sdk.kernel.model.PluginState;
import io.sunshower.sdk.kernel.model.PluginUploadFault;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("plugins")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface PluginEndpoint {

  @GET
  @Path("/")
  List<PluginDescriptorElement> list();
  
  @GET
  @Path("{id}")
  PluginDescriptorElement get(@PathParam("id") Identifier id);
  

  @POST
  @Path("deploy")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  void deploy(@Context HttpServletRequest req, @Context HttpServletResponse res)
      throws PluginUploadFault;

  @PUT
  @Path("{id}/{state}")
  void setState(@PathParam("id") String pluginId, @PathParam("state") PluginState state);

  @GET
  @Path("{id}/state")
  PluginState getState(@PathParam("id") String id);
}
